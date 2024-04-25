package com.tempKafka.Controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.tempKafka.Repo.*;
import com.tempKafka.Service.AWSClientConfigService;
import com.tempKafka.Service.StorageManagementService;
import com.tempKafka.elastic.ElasticService;
import com.tempKafka.elastic.EsRequest;
import com.tempKafka.elastic.EsResponse;
import com.tempKafka.dto.*;
import com.tempKafka.model.*;

import org.elasticsearch.index.query.QueryBuilders;
import org.json.JSONArray;
import org.json.JSONObject;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.kafka.Notification;

import java.io.*;
import java.util.zip.ZipEntry;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.io.FileUtils;
import com.tempKafka.modelMySql.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/clarosOne")
public class Controller {

	@Value("${face-service}")
	String faceService;

	@Value("${audio-service}")
	String audioRec;

	@Value("${mapUrl}")
	String mapUrl;

	@Autowired
	ElasticService elasticService;

	@Autowired
	ImgRepository imgRepo;

	@Autowired
	CrimeRepo crimeRepo;

	@Autowired
	UserDataRepo userDataRepo;

	@Autowired
	SaveAudioRepo saveAudioRepo;

	@Autowired
	SaveImageRepo saveImageRepo;

	@Autowired
	UnclassifiedImageRepo unclassifiedImageRepo; // unclassifiedImage

	@Autowired
	ImgRepository imgRepository; // image SearchM

	Map<String, Object> ans = new HashMap<>();

	String searchIndex = "crime_user";
	String imageSearchIndex = "face_rec_criminal";

	@Autowired
	StorageManagementService smService;
	
	@Autowired
	ClarosUserMySQLRepo cUserSQLRepo;
	//////////
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/claros";
	private static final String JDBC_USERNAME = "root";
	private static final String JDBC_PASSWORD = "root";
	
	private static void insertProduct(int i, Connection connection, String name, String userid, Long age, String description, String monitored, String score) throws SQLException {
		String query = "INSERT INTO products (id, name, userid, age, description, monitored, score) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, i);
		preparedStatement.setString(2, name);
		preparedStatement.setString(3, userid);
		preparedStatement.setLong(4, age);
		preparedStatement.setString(5, description);
		preparedStatement.setString(6, monitored);
		preparedStatement.setString(7, score);

		preparedStatement.executeUpdate();
	}
	
	@PostMapping("/addCrimeUserss")
	public ResponseEntity<?> addCrimeUser() {
		Iterable<ClarosUsers> findAll = cUserRepo.findAll();
		for (ClarosUsers as : findAll) {
			as.setFriendsID(new ArrayList<>());
			as.setEnemyID(new ArrayList<>());
			cUserRepo.save(as);
			
		}
		return new ResponseEntity<>(cUserRepo.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/test1")
	public void test1() throws SQLException {
//		String apiUrl = "http://3.7.32.64:9200";
		Iterable<ClarosUsers> findAllElastic =cUserRepo.findAll();
		int ifs=0;
		List<ClarosUsers> apiUrl= new ArrayList<>();
		for(ClarosUsers a: findAllElastic)
		{
		   ifs++;	
		   apiUrl.add(a);
			if(ifs==5)break;
			
		
		}

	
			// Retrieve data from the API
			String jsonResponse = apiUrl.toString();

			// Parse JSON response
			JSONArray productsArray = new JSONArray(jsonResponse);

			// Connect to MySQL database
			Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);

			// Iterate through products and insert into database
			for (int i = 0; i < productsArray.length(); i++) {
				JSONObject productObject = productsArray.getJSONObject(i);

				String name = productObject.getString("name");
				String userid = productObject.getString("userid");
				Long age = productObject.getLong("age");
				String description = productObject.getString("description");
				String monitored = productObject.getString("monitored");
				String score = productObject.getString("score");
//				String dob = productObject.get("dob");
//				JSONObject ratingObject = productObject.getJSONObject("score");
//				double ratingRate = ratingObject.getDouble("dob");
//				int ratingCount = ratingObject.getInt("count");

				// Insert into database
				insertProduct(i+1, connection, name, userid, age, description, monitored, score);
			}

			connection.close();
			System.out.println("Data inserted successfully.");

		
	}

//	private static String fetchJson(String apiUrl) throws IOException {
//		StringBuilder jsonResponse = new StringBuilder();
////		URL url = new URL(apiUrl);
////		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
////		conn.setRequestMethod("GET");
////		InputStream inputStream = conn.getInputStream();
////		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//		String line;
////		while ((line = reader.readLine()) != null) {
////			jsonResponse.append(line);
////		}
////		reader.close();
//		return jsonResponse.toString();
//	}

	

	
	
	
	//////////
	
	@GetMapping("/MySQLtoElastic")
	public Iterable<ClarosUsers> MySQLToElastic() {
		Iterable<ClarosUsersJ> findAllElastic =cUserSQLRepo.findAll();
		int i=0;
		for(ClarosUsersJ a: findAllElastic)
		{
		   i++;	
			cUserRepo.save(new ClarosUsers(a));
			if(i==5)break;
			
		
		}
		
		return cUserRepo.findAll();
	}
	
	@GetMapping("/elasticToMySQL")
	public List<ClarosUsersJ> elasticToMySQL() {
		Iterable<ClarosUsers> findAllElastic = cUserRepo.findAll();
		int i=0;
		for(ClarosUsers a: findAllElastic)
		{
		   i++;	
			cUserSQLRepo.save(new ClarosUsersJ(a));
			
		
		}
		
		return cUserSQLRepo.findAll();
	}

	@GetMapping("/lsttoString")
	public String lsttoString() {
		List<String> a= new ArrayList<>();
		return a.toString();
	}
	
	@GetMapping("/associates/incident/VideosAll")
	public ResponseEntity<?> allVideosofUser(@RequestHeader String userid, @RequestHeader String Authorization) {
		ans = new HashMap<>();
		List<AllDataDTO> allDataList = new ArrayList<>();

		String token = Authorization.replace("Bearer ", "");

		System.out.println("Preview");
		List<Object> previewImages = new ArrayList<>();
		List<UnclassifiedImage> findAll = unclassifiedImageRepo.findAllByType("video");
		for (UnclassifiedImage img : findAll) {
			AllDataDTO allDataDTO = new AllDataDTO();
			allDataDTO.setId(img.getId());
			allDataDTO.setType(img.getType());
			

				String video_path = img.getVideo_path();
				if (video_path != null && !video_path.contains("http")) {
					video_path = "http://http://3.7.32.64:9000/" + video_path;
				}

				allDataDTO.setPath(video_path);
			

			allDataDTO.setTime(img.getTime() != null ? img.getTime() : LocalDateTime.now());

			List<Object> faceDetails = img.getFace_details();
			if (faceDetails != null) {

				HashMap<String, Object> possibleMatchScores = new HashMap<>();
				Set<NameDTO> nameMatcher = new HashSet<>();
				for (Object face : faceDetails) {
					Map<String, Object> fd = (Map<String, Object>) face;
					possibleMatchScores
							.putAll((Map<String, Object>) fd.getOrDefault("possible_match_score", new HashMap<>()));

					NameDTO matches = new NameDTO();
//						for(String id: fd.keySet()) {						

					HashMap<String, Integer> hash = (HashMap<String, Integer>) fd.get("possible_match_score");

					for (String matchKey : hash.keySet()) {
						Optional<ClarosUsers> byId = cUserRepo.findByUserid(matchKey);
						if (byId.isPresent()) {
							String name = byId.get().getName();
							matches.setName(name);

							matches.setUserid(matchKey);
//									System.err.println("%% "+hash.get(matchKey));
							matches.setMatchPercentage(hash.get(matchKey));
							nameMatcher.add(matches);
						}

					}

//							matches.setMatchPercentage((Integer.parseInt(fd.get("possible_match_score")));
//						}
//	                    
					List<NameDTO> collect = nameMatcher.stream().collect(Collectors.toList());
					allDataDTO.setNameDto(collect);

					System.out.print(".");
				}

				allDataDTO.setPossible_match_score(possibleMatchScores);
			}

			allDataList.add(allDataDTO);

		}

		System.out.println("\nReturn" + allDataList.size());
		ans.put("id", userid);
		ans.put("data", allDataList);
		return new ResponseEntity<>(ans, HttpStatus.OK);
	}
//			ans = new HashMap<>();
//			ans.put("images", fAns);
//			

//			return new ResponseEntity<>(ans, HttpStatus.OK);

	@GetMapping("/associates/incident/Preview")
	public ResponseEntity<?> previewImagesofUser(@RequestHeader String type, @RequestHeader String Authorization) {
		ans = new HashMap<>();
		List<AllDataDTO> allDataList = new ArrayList<>();
		ans.put("userid", type);
		String token = Authorization.replace("Bearer ", "");

		System.out.println("Preview");
		List<Object> previewImages = new ArrayList<>();
		List<UnclassifiedImage> findAll = unclassifiedImageRepo.findAllByType(type);
		for (UnclassifiedImage img : findAll) {
			AllDataDTO allDataDTO = new AllDataDTO();
			allDataDTO.setId(img.getId());
			allDataDTO.setType(img.getType());
			if (img.getType() != null && img.getType().equals("image"))
				allDataDTO.setPath(img.getImg());
			else {

				String video_path = img.getVideo_path();
				if (video_path != null && !video_path.contains("http")) {
					video_path = "http://http://3.7.32.64:9000/" + video_path;
				}

				allDataDTO.setPath(video_path);
			}

			allDataDTO.setTime(img.getTime() != null ? img.getTime() : LocalDateTime.now());

			List<Object> faceDetails = img.getFace_details();
			if (faceDetails != null) {

				HashMap<String, Object> possibleMatchScores = new HashMap<>();
				Set<NameDTO> nameMatcher = new HashSet<>();
				for (Object face : faceDetails) {
					Map<String, Object> fd = (Map<String, Object>) face;
					possibleMatchScores
							.putAll((Map<String, Object>) fd.getOrDefault("possible_match_score", new HashMap<>()));

					NameDTO matches = new NameDTO();
//						for(String id: fd.keySet()) {						

					HashMap<String, Integer> hash = (HashMap<String, Integer>) fd.get("possible_match_score");

					for (String matchKey : hash.keySet()) {
						Optional<ClarosUsers> byId = cUserRepo.findByUserid(matchKey);
						if (byId.isPresent()) {
							String name = byId.get().getName();
							matches.setName(name);

							matches.setUserid(matchKey);
//									System.err.println("%% "+hash.get(matchKey));
							matches.setMatchPercentage(hash.get(matchKey));
							nameMatcher.add(matches);
						}

					}

//							matches.setMatchPercentage((Integer.parseInt(fd.get("possible_match_score")));
//						}
//	                    
					List<NameDTO> collect = nameMatcher.stream().collect(Collectors.toList());
					allDataDTO.setNameDto(collect);

					System.out.print(".");
				}

				allDataDTO.setPossible_match_score(possibleMatchScores);
			}

			allDataList.add(allDataDTO);

		}

		System.out.println("\nReturn" + allDataList.size());
		ans.put("data", allDataList);
		return new ResponseEntity<>(ans, HttpStatus.OK);
	}

	@GetMapping("/getFilebyte")
	public ResponseEntity<?> getFilebyte(@RequestHeader String fileId, @RequestHeader String type,
			HttpServletRequest request) {
		Map<String, Object> ans = new HashMap<>();
		ans.put("error", "File Not Found");

		try {
			// transcribe audio video
			String token = request.getHeader("Authorization").replace("Bearer ", "");
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
			ZipOutputStream zOS = new ZipOutputStream(bufferedOutputStream);
			HttpHeaders httpHeaders = new HttpHeaders();

			String bucketName = "", key = "";
			// userData for transcribe and audio
			// unclassified for video
			if (type.equalsIgnoreCase("video")) {
				Optional<UnclassifiedImage> file = unclassifiedImageRepo.findById(fileId);
				if (!file.isPresent())
					return new ResponseEntity<>(ans, HttpStatus.INTERNAL_SERVER_ERROR);
				String video_path = file.get().getVideo_path();
				if (video_path == null || video_path.length() == 0)
					return new ResponseEntity<>(ans, HttpStatus.INTERNAL_SERVER_ERROR);
				if (video_path.contains("http")) {
					String[] split = video_path.split("/", 5);
					bucketName = split[3];
					key = split[4];
				} else {
					String[] split = video_path.split("/", 2);
					bucketName = split[0];
					key = split[1];
				}
			} else {
				Optional<UserData> file = userDataRepo.findById(fileId);
				if (!file.isPresent())
					return new ResponseEntity<>(ans, HttpStatus.INTERNAL_SERVER_ERROR);
				String video_path = file.get().getFileUrl();
				if (video_path == null || video_path.length() == 0)
					return new ResponseEntity<>(ans, HttpStatus.INTERNAL_SERVER_ERROR);
				if (video_path.contains("http")) {
					String[] split = video_path.split("/", 5);
					bucketName = split[3];
					key = split[4];
				} else {
					String[] split = video_path.split("/", 2);
					bucketName = split[0];
					key = split[1];
				}
			}

			S3ObjectInputStream inputStream = smService.downloadDoc(bucketName, key, token, 0l);
			IOUtils.copy(inputStream, byteArrayOutputStream);
			IOUtils.closeQuietly(bufferedOutputStream);
			IOUtils.closeQuietly(byteArrayOutputStream);
			httpHeaders.set("Content-Disposition", "attachment; filename=\"" + key + "\"");
			IOUtils.closeQuietly(inputStream);
//		System.out.println(byteArrayOutputStream);
//		createUserLog(deptRole, "File/Folder Download", ReqType.DOWNLOAD, "Downloaded file " + dName);
			return ResponseEntity.ok().headers(httpHeaders).body(byteArrayOutputStream.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>(ans, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/clearDB")
	public ResponseEntity<?> oneHello() {
//		unclassifiedImageRepo
		unclassifiedImageRepo.deleteAll();
		userDataRepo.deleteAll();
		sointRepo.deleteAll();

		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@GetMapping("/test")
	public String test() {
		return "ClarosTestAWSNotification";
	}

	@Autowired
	private ClarosUsersRepo cUserRepo;

	@GetMapping("/renameName")
	public Integer renameName() {
		System.out.println("RenameName");

		Iterable<ClarosUsers> findAll = cUserRepo.findAll();
		int i = 0;
		for (ClarosUsers a : findAll) {
			i++;
			a.setName(a.getName().replace(" ", "_"));
			cUserRepo.save(a);
		}

		return i;
	}

	@GetMapping("/associates/incidentNegative")
	public ResponseEntity<?> incidentNegatice(@RequestHeader String id,
			@RequestHeader(required = false) String display_keyword) {
		ans = new HashMap<>();

		if (display_keyword == null || display_keyword.length() == 0)
			display_keyword = "show_all_images";

		Optional<ClarosUsers> findByuserId = cUserRepo.findByUserid(id);

		if (findByuserId.isPresent()) {
			String Name = findByuserId.get().getName().replace("_", " ");
			String mapIframeSrc = mapUrl
//					"http://3.7.32.64:5601/app/dashboards#/view/dd90af64-92d0-402a-be4a-36e9b526bbb0?embed=true&_g=("
//					"http://11.0.0.108:5601/app/dashboards#/view/5e758f80-d083-11ee-8357-c379c6d69552?embed=true&_g=("
					+ "filters:!(('$state':(store:appState),meta:(alias:!n,disabled:!f,key:Name,negate:!f,type:phrase),"
					+ "query:(match:(userid:(query:" + "\'" + id + "\'" + ",type:phrase))))))";

			List<FriendsObject> fO = new ArrayList<>();
			for (String a : findByuserId.get().getEnemyID()) {
				Optional<ClarosUsers> fOs = cUserRepo.findByUserid(a);

				if (!fOs.isPresent())
					continue;

				int presentAge = 0;
				if (fOs.get().getDob() != null) {
					LocalDate dob = LocalDate.parse((String) fOs.get().getDob());
					presentAge = LocalDate.now().compareTo(dob);
				}

				fO.add(new FriendsObject(fOs.get().getName().replace("_", " "), "" + presentAge, fOs.get().getUserid(),
						fOs.get().getImage()));

			}

			SuspectIframe sus = new SuspectIframe(Name, fO, mapIframeSrc);
			sus.setDescription(findByuserId.get().getDescription());
			ans.put("suspect", sus);
			ans.put("image", findByuserId.get().getImage());
			ans.put("id", id);
			ans.put("display_keyword", display_keyword);
			return new ResponseEntity<>(ans, HttpStatus.OK);

		}
		ans.put("suspect", new SuspectIframe());
		ans.put("message", "UserNotFound");
		return new ResponseEntity<>(ans, HttpStatus.OK);
	}
	//

	public List<RadialTree> addLevel(String userid, String relationType, Set<String> friendsListSet) {
		System.out.println(friendsListSet);
		System.out.print("userid: " + userid + ":");
		List<RadialTree> fans = new ArrayList<>();
		Optional<ClarosUsers> user = cUserRepo.findByUserid(userid);
		if (!user.isPresent())
			return new ArrayList<>();
		System.out.println(user.get().getName());

		List<String> friendsid = new ArrayList<>();

		if (relationType.equals("true")) {
			if (user.get().getFriendsID() == null)
				return new ArrayList<>();
			friendsid = user.get().getFriendsID();
		} else {
			if (user.get().getEnemyID() == null)
				return new ArrayList<>();
			friendsid = user.get().getEnemyID();
		}
		friendsListSet.add(userid);
		for (String friends : friendsid) {
			System.out.println("friends");
			Optional<ClarosUsers> friend = cUserRepo.findByUserid(friends);
			if (!friend.isPresent())
				continue;
			if (friendsListSet.contains(friends))
				continue;
			System.out.println(friend.get().getName());
			friendsListSet.add(friends);
			fans.add(new RadialTree(friend.get().getUserid(), friend.get().getName().replace("_", " "),
					"" + friend.get().getAge(), user.get().getUserid(), friend.get().getImage(), relationType));
		}

		return fans;
	}

	@GetMapping("/associates/incident/graph")
	public ResponseEntity<?> incidentpositive123(@RequestHeader String id,
			@RequestHeader(required = false) String display_keyword, @RequestHeader String relationType) {
		Optional<ClarosUsers> user = cUserRepo.findByUserid(id);
		ans = new HashMap<>();
		if (!user.isPresent()) {
			ans.put("message", "User Not Found");
			ans.put("error", "User Not Found");
			return new ResponseEntity<>(ans, HttpStatus.BAD_REQUEST);
		}

		Set<String> friendsListSet = new HashSet<>();
		List<RadialTree> fans = new ArrayList<>();
		fans.add(new RadialTree(user.get().getUserid(), user.get().getName().replace("_", " "),
				"" + user.get().getAge(), null, user.get().getImage(), relationType));

		// level one
		fans.addAll(addLevel(id, relationType, friendsListSet));

		// level two
		List<String> userList = new ArrayList<>();
		if (relationType.equals("true"))
			userList = user.get().getFriendsID();
		else
			userList = user.get().getEnemyID();

		for (String friend : user.get().getFriendsID()) {
			fans.addAll(addLevel(friend, relationType, friendsListSet));
		}
		ans.put("radialTree", fans);
		ans.put("friendsListSet", friendsListSet);
		return new ResponseEntity<>(ans, HttpStatus.OK);
	}

	@GetMapping("/associates/incident")
	public ResponseEntity<?> incident(@RequestHeader String id,
			@RequestHeader(required = false) String display_keyword) {
		System.out.println("associates::incident" + id + " :" + display_keyword);
		if (display_keyword == null || display_keyword.length() == 0)
			display_keyword = "show_all_images";

		if (display_keyword.equals("show_negitive_relations_graph"))
			return incidentNegatice(id, display_keyword);

		ans = new HashMap<>();

		Optional<ClarosUsers> findByuserId = cUserRepo.findByUserid(id);
		if (findByuserId.isPresent()) {
			String Name = findByuserId.get().getName().replace("_", " ");
			String mapIframeSrc = mapUrl
//					"http://3.7.32.64:5601/app/dashboards#/view/dd90af64-92d0-402a-be4a-36e9b526bbb0?embed=true&_g=("
//					"http://11.0.0.108:5601/app/dashboards#/view/5e758f80-d083-11ee-8357-c379c6d69552?embed=true&_g=("
					+ "filters:!(('$state':(store:appState),meta:(alias:!n,disabled:!f,key:Name,negate:!f,type:phrase),"
					+ "query:(match:(userid:(query:" + "\'" + id + "\'" + ",type:phrase))))))&show-time-filter=true";

			List<FriendsObject> fO = new ArrayList<>();
			for (String a : findByuserId.get().getFriendsID()) {
				Optional<ClarosUsers> fOs = cUserRepo.findByUserid(a);

				if (!fOs.isPresent())
					continue;

				Object imageUser = fOs.get().getImage();
				int presentAge = 0;
				if (fOs.get().getDob() != null) {
					LocalDate dob = LocalDate.parse((String) fOs.get().getDob());
					presentAge = LocalDate.now().compareTo(dob);
				}
				fO.add(new FriendsObject(fOs.get().getName().replace("_", " "), "" + presentAge, fOs.get().getUserid(),
						imageUser));

			}

			SuspectIframe sus = new SuspectIframe(Name, fO, mapIframeSrc);

//				sus.setDescription(a.getDescription());
			Object info = findByuserId.get().getInformation();
			sus.setInformation(info);
			ans.put("suspect", sus);
			ans.put("image", findByuserId.get().getImage());
			ans.put("id", id);
			ans.put("display_keyword", display_keyword);
			return new ResponseEntity<>(ans, HttpStatus.OK);

		}
		ans.put("suspect", new SuspectIframe());
		ans.put("message", "UserNotFound");
		return new ResponseEntity<>(ans, HttpStatus.OK);
	}

	@GetMapping("/autoSuggestName")
	public ResponseEntity<?> autoSuggestName(@RequestHeader String Name) {
		System.out.println("autoSuggestName: " + Name);
		List<ClarosUsers> fans = cUserRepo.findAllIgnoreCaseByNameContainingIgnoreCase(Name.replace(" ", "_"));

		List<String> sugName = new ArrayList<>();
		for (ClarosUsers a : fans)
			sugName.add(a.getName().replaceAll("_", " "));
		ans = new HashMap<>();
		if (sugName.size() == 0)
			ans.put("message", "No Value Found");
		ans.put("listdata", sugName);

		return new ResponseEntity<>(ans, HttpStatus.OK);
	}

	@GetMapping("/autoSuggestUserId")
	public ResponseEntity<?> autoSuggestUserId(@RequestHeader String userId) {
		System.out.println("autoSuggestUserId: " + userId);
		List<ClarosUsers> fans = cUserRepo.findAllIgnoreCaseByUseridContainingIgnoreCase(userId.trim());
		List<String> sugName = new ArrayList<>();
		for (ClarosUsers a : fans)
			sugName.add(a.getUserid());
		ans = new HashMap<>();
		if (sugName.size() == 0)
			ans.put("message", "No Value Found");
		ans.put("listdata", sugName);
		return new ResponseEntity<>(ans, HttpStatus.OK);
	}

	@GetMapping("/susSearch")
	public ResponseEntity<?> susSearch(@RequestHeader String userid) {
		System.out.println("susSearch");
		ans = new HashMap<>();
		List<ClarosUsers> as = cUserRepo.findAllByUserid(userid);
		for (ClarosUsers a : as) {
			a.setName(a.getName().replaceAll("_", " "));
			as.add(a);
			return new ResponseEntity<>(a, HttpStatus.OK);
		}
		ans.put("listData", as);

		return new ResponseEntity<>(ans, HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping("/searchSus")
	public ResponseEntity<?> classedFile(@RequestBody SusSearch threeVals) {
		System.out.println(threeVals);

		ans = new HashMap<>();
		ans.put("listData", new ArrayList<>());
		BoolQueryBuilder suggestionbq = new BoolQueryBuilder();
		int a = 0;

		String Name = threeVals.getName();
		String userId = threeVals.getUserid();
		Integer fAge = threeVals.getFromAge();
		Integer tAge = threeVals.getToAge();

		if (fAge != null && tAge != null && fAge > 0 && tAge > 0) {
			BoolQueryBuilder suggestionage = new BoolQueryBuilder();
			suggestionage.filter((QueryBuilders.rangeQuery("age").gte(fAge).lte(tAge)));
			suggestionbq.must(suggestionage);
		}
		if (Name != null && Name.length() > 0) {
			BoolQueryBuilder suggestionName = new BoolQueryBuilder();
			Name = Name.trim().replaceAll(" ", "_");
			suggestionName.should(QueryBuilders.wildcardQuery("Name", "*" + Name + "*"));
			suggestionName.should(QueryBuilders.matchPhrasePrefixQuery("Name", Name));
			suggestionbq.must(suggestionName);
		}
		if (userId != null && userId.length() > 0) {
			BoolQueryBuilder suggestionuserId = new BoolQueryBuilder();
			userId = userId.trim().replaceAll(" ", "_");
			suggestionuserId.should(QueryBuilders.wildcardQuery("userid", "*" + userId + "*"));
			suggestionuserId.should(QueryBuilders.matchPhrasePrefixQuery("userid", userId));
			suggestionbq.must(suggestionuserId);
		}

		EsRequest esRequest = new EsRequest().queryBuilder(suggestionbq).index(searchIndex);
		List<ClarosUsers> as = new ArrayList<>();
		try {
			EsResponse esResponse = elasticService.searchES(esRequest, 0, 1000);
			System.out.println("----------" + esResponse.getTotalHits());
			float maxScore = esResponse.getMaxScore();
			for (org.elasticsearch.search.SearchHit search : esResponse.getHits()) {

				Map<String, Object> sourceMap = search.getSourceAsMap();
				if (sourceMap.get("Name") == null || sourceMap.get("userid") == null || sourceMap.get("age") == null) {
					continue;
				}
				Optional<ImageSearchM> img = imgRepo
						.findFirstByUseridAndDisplayPicture((String) sourceMap.get("userid"), true);
				if (!img.isPresent())
					img = imgRepo.findFirstByUserid((String) sourceMap.get("userid"));

				int presentAge = Integer.parseInt(sourceMap.get("age").toString());

				if (sourceMap.get("dob") != null) {
//					LocalDate dob = (LocalDate) sourceMap.get("dob");
					LocalDate dob = LocalDate.parse((String) sourceMap.get("dob"));
					presentAge = LocalDate.now().compareTo(dob);
				}

				as.add(new ClarosUsers(null, sourceMap.get("Name").toString().replaceAll("_", " "),
						sourceMap.get("userid").toString(), presentAge, (String) sourceMap.get("description"),
						sourceMap.get("image"), sourceMap.get("information"), false,
						(100 * search.getScore() / maxScore) + "%"));

			}
		} catch (ElasticsearchStatusException e1) {
			System.out.println("errorAdvanceSearch");
			e1.printStackTrace();
			throw e1;
		} catch (IOException e1) {
			System.out.println("errorAdvanceSearchIO:");
			e1.printStackTrace();
			return null;
		}

		System.out.println("Searxch: No error");
		ans.put("listData", as);

		return new ResponseEntity<>(ans, HttpStatus.OK);

	}

	@Autowired
	UnclassifiedImageRepo unImageRepo;

	@PostMapping("/associates/incident/Preview/save")
	public ResponseEntity<?> previewImageSave(@RequestHeader String userid, @RequestHeader String imageId,
			@RequestBody PreviewSaveDTO previewSaveDTO) {
		ans = new HashMap<>();
		System.out.println(previewSaveDTO.toString());
		UnclassifiedImage unImg = unImageRepo.findById(imageId).get();
		List<String> userIds = new ArrayList<>();
//		ans.put("main_image", unImg.getBoxImg());
//		ans.put("type", unImg.getType());
		List<FaceDetailsUnclassified> faceDet = new ArrayList<>();
		List<Object> updatedFaceDetails = new ArrayList<>();
		int flag = 0, fcount = 0;
		Map<String, Object> faceEncoding = new HashMap<>();
		for (Object facesDet : unImg.getFace_details()) {

			HashMap<String, Object> abf = (HashMap<String, Object>) facesDet;

			if (abf.get("selected") != null && (Boolean) abf.get("selected")) {
				fcount++;
				userIds.add((String) abf.get("selectedpossibleMatchId"));
			} else if (previewSaveDTO.getFaceid() == (Integer) abf.get("face_no")) {
				flag++;
				fcount++;
				abf.put("selected", true);
				abf.put("selectedpossibleMatchId", previewSaveDTO.getFaceuserid());
				userIds.add(previewSaveDTO.getFaceuserid());
			}

			FaceDetailsUnclassified fDU = new FaceDetailsUnclassified((Integer) abf.get("face_no"), abf.get("face_img"),
					null, (List<String>) abf.get("possible_matches"),
					(String) abf.get("selectedpossibleMatchId") != null ? abf.get("selectedpossibleMatchId").toString()
							: null,
					abf.get("selected") != null ? (Boolean) abf.get("selected") : false);
			if (fDU.getPossible_matches().size() == 0) {
				flag++;
				abf.put("selected", true);
				abf.put("selectedpossibleMatchId", "Others");
			}
			if (abf.get("face_no") != null && (Integer) abf.get("face_no") > 0)
				faceDet.add(fDU);

			updatedFaceDetails.add(abf);
		}
		System.out.println(":::" + fcount);
		if (flag > 0) {
			unImg.setFace_details(updatedFaceDetails);
			if (fcount >= unImg.getFace_count() && fcount > 0) {
				System.out.println("all Saved");
				ImageSearchM imgNew = new ImageSearchM();
				imgNew.setImg(unImg.getImg());

				for (String userP : userIds) {
					imgNew.setUserid(userP);
					ImageSearchM findFirstByUserid = imgRepo.findFirstByUserid(userP).get();
					imgNew.setFace_coordinates(findFirstByUserid.getFace_coordinates());
					imgNew.setFace_encoding(findFirstByUserid.getFace_encoding());
					imgRepo.save(imgNew);
				}
				imgNew.setUserid(unImg.getUploadid());
				if (unImg.getType().equals("image"))
					unImageRepo.deleteById(imageId);
				ans.put("data", null);
				ans.put("message", "success");
				return new ResponseEntity<>(ans, HttpStatus.OK);
			}
//		unImageRepo.deleteById(imageId);
//		else
			unImageRepo.save(unImg);
		}
		ans.put("data", null);
		ans.put("message", "success");
		return new ResponseEntity<>(ans, HttpStatus.OK);
	}

	@PostMapping("/associates/incident/Preview/menu")
	public ResponseEntity<?> previewImagesofUser(@RequestBody List<String> ids) {
		ans = new HashMap<>();
		List<ClarosUsers> menu = new ArrayList<>();
		for (String id : ids) {
			if (id.equals("Others"))
				continue;
			List<ClarosUsers> findAllByUserid = cUserRepo.findAllByUserid(id);
			for (ClarosUsers a : findAllByUserid) {
				a.setImage(a.getImage());
				a.setName(a.getName().replace("_", " "));
				menu.add(a);
			}
		}
		return new ResponseEntity<>(menu, HttpStatus.OK);
	}

	@GetMapping("/associates/incident/Preview/details")
	public ResponseEntity<?> previewImageofUser(@RequestHeader String userid, @RequestHeader String imageid,
			@RequestHeader String Authorization) {
		ans = new HashMap<>();
		String token = Authorization.replace("Bearer ", "");
		System.out.println("PreviewDetails");
		Optional<UnclassifiedImage> ab = unImageRepo.findById(imageid);
		if (!ab.isPresent()) {
			ans.put("message", "File Not Present");
			ans.put("userid", userid);

			return new ResponseEntity<>(ans, HttpStatus.OK);

		}
		UnclassifiedImage a = ab.get();

		ans.put("type", a.getType());
		if (a.getType().equals("video")) {
			String video_path = a.getVideo_path();
			if (video_path != null && !video_path.contains("http")) {
				video_path = "http://http://3.7.32.64:9000/" + video_path;
			}

			ans.put("main_image", video_path);
		} else
			ans.put("main_image", a.getBoxImg());

		List<FaceDetailsUnclassified> faceDet = new ArrayList<>();
		for (Object facesDet : a.getFace_details()) {
			HashMap<String, Object> abf = (HashMap<String, Object>) facesDet;

			FaceDetailsUnclassified fDU = new FaceDetailsUnclassified((Integer) abf.get("face_no"), abf.get("face_img"),
					null, (List<String>) abf.get("possible_matches"),
					(String) abf.get("selectedpossibleMatchId") != null ? abf.get("selectedpossibleMatchId").toString()
							: null,
					abf.get("selected") != null ? (Boolean) abf.get("selected") : false);
			faceDet.add(fDU);
		}
		ans.put("data", faceDet);
		ans.put("userid", userid);
		ans.put("imageid", imageid);

		return new ResponseEntity<>(ans, HttpStatus.OK);

	}

	@GetMapping("/getFile/{str}")
	public ResponseEntity<?> fileGet(@PathVariable String str) {
		File file = new File("./file.docx");

		try {
			if (file.createNewFile()) {
				System.out.println(" File Created");
			} else {
				System.out.println("File Found");
			}

			Files.write(Paths.get("./file.docx"), str.getBytes());
			Path paths = Paths.get("./file.docx");
			byte[] videoData = Files.readAllBytes(paths);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", "newFile" + ".docx");

			return ResponseEntity.ok().headers(headers).body(videoData);

		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}

	}

	@GetMapping("/getVideo/{imageid}")
	public ResponseEntity<?> getVideo(@PathVariable String imageid) throws IOException {
//		UnclassifiedImage a = unImageRepo.findById(imageid).get();
		String path = "C:\\Users\\Administrator\\Desktop\\face recog\\app\\video_file\\";
		System.out.println("Paths" + path);
		Path paths = Paths.get(path + imageid + ".mp4");

		byte[] data = null;
		try {
			byte[] videoData = Files.readAllBytes(paths);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", "vid" + ".mp4");

			return ResponseEntity.ok().headers(headers).body(videoData);

		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

//	@GetMapping("/associates/incident/Preview")
//	public ResponseEntity<?> previewImagesofUser(@RequestHeader String type,@RequestHeader String Authorization) {
//		ans = new HashMap<>();
//		ans.put("userid", type);
//		String token = Authorization.replace("Bearer ", "");
//
//		System.out.println("Preview");
//		List<Object> previewImages = new ArrayList<>();
//		List<UnclassifiedImage> findAll = unImageRepo.findAllByType(type);
//		for (UnclassifiedImage a : findAll) {
//			Map<String, Object> temp = new HashMap<>();
//			Map<String, byte[]> tvid = new HashMap<>();
//			temp.put("id", a.getId());
//			temp.put("main_image", a.getBoxImg());
//			temp.put("type", a.getType());
//			if (a.getType() != null && a.getType().equals("video")) {
//				System.out.println("video" + a.getVideo_path());
//				temp.put("type", a.getType());
////                tvid.put("data", getVideo(a.getId()));
//				String video_path=a.getVideo_path();
//				if (video_path!=null && !video_path.contains("http")) {
//					video_path= "http://http://3.7.32.64:9000/"+video_path;
//				}
//				temp.put("video_buffer", video_path);
//			}
////			temp.put("faceDetails", faceDetailsUnclassified);
//
//			previewImages.add(temp);
//		}
////		System.out.println(userid + " : " + findAll);
//		ans.put("data", previewImages);
//		System.out.println(previewImages);
//		return new ResponseEntity<>(ans, HttpStatus.OK);
//	}

	@GetMapping("/associates/incident/ImagesAll")
	public ResponseEntity<?> allImagesofUser(@RequestHeader String userid) {
		System.out.println("incident ImagesAll");
		List<ImageSearchM> ansImage = imgRepo.findAllByUserid(userid);
		List<ImageDTO> fAns = new ArrayList<>();
		for (ImageSearchM a : ansImage) {
			fAns.add(new ImageDTO(a.getImg(), a.getId()));
		}
		ans = new HashMap<>();
		ans.put("images", fAns);
		ans.put("id", userid);

		return new ResponseEntity<>(ans, HttpStatus.OK);
	}

//	@GetMapping("/associates/incident/VideosAll")
//	public ResponseEntity<?> allVideosofUser(@RequestHeader String userid,@RequestHeader String Authorization) {
//		System.out.println("incident VideosAll");
//		String token = Authorization.replace("Bearer ","");
//		List<UnclassifiedImage> ansImage = unclassifiedImageRepo.findAllByType("video");
//		List<ImageDTO> fAns = new ArrayList<>();
//		Map<String, String> idPath = new HashMap<>();
//		for (UnclassifiedImage a : ansImage) {
//			List<Object> faceDetails = a.getFace_details();
//			//tobeDeleted after Buckets are private
//			String video_path=a.getVideo_path();
//			if (video_path!=null && !video_path.contains("http")) {
//				video_path= "http://http://3.7.32.64:9000/"+video_path;
//			}
//			//
//			if (faceDetails != null) {
//				List<NameDTO> nameMatcher = new ArrayList<>();
//				HashMap<String, Object> possibleMatchScores = new HashMap<>();
//				for (Object face : faceDetails) {
//					Map<String, Object> fd = (Map<String, Object>) face;
//					List<String> userids = fd.get("possible_matches") != null
//							? (List<String>) fd.get("possible_matches")
//							: new ArrayList<>();
//
//					for (String id : userids) {
//						if (id.equals(userid))  idPath.put(video_path, a.getId());
////							idPath.put(a.getVideo_path(), a.getId());
//					}
//
//				}
//			}
//
//		}
//
//		for (Map.Entry<String, String> entry : idPath.entrySet()) {
//			fAns.add(new ImageDTO(entry.getKey(), entry.getValue()));
//		}
//
//		ans = new HashMap<>();
//		ans.put("images", fAns);
//		ans.put("id", userid);
//
//		return new ResponseEntity<>(ans, HttpStatus.OK);
//	}

	@PostMapping("/setDP")
	public ResponseEntity<?> setDP(@RequestHeader String userid, @RequestHeader String imageId) {
		System.out.println("setDP");
		Optional<ImageSearchM> oldImage = imgRepo.findFirstByUseridAndDisplayPicture(userid, true);
		Optional<ImageSearchM> newImage = imgRepo.findById(imageId);
		if (oldImage.isPresent()) {
			System.out.println("imgRepoFound" + oldImage.get().getId() + " : ");
			ImageSearchM a = oldImage.get();
			a.setDisplayPicture(false);
			imgRepo.save(a);
			System.out.println("saved");
		}
		newImage = imgRepo.findById(imageId);
		ans = new HashMap<>();

		if (newImage.isPresent()) {
			System.out.println("Found" + newImage.get().getId() + " :" + newImage.get().getDisplayPicture());
			ImageSearchM a = newImage.get();
			a.setDisplayPicture(true);
			imgRepo.save(a);
			ClarosUsers cuser = cUserRepo.findByUserid(userid).get();
			cuser.setImage(newImage.get().getImg());
			cUserRepo.save(cuser);
			System.out.println("saved");
		} else {
			ans.put("error", "ImageId: " + imageId + " Invalid ");
			return new ResponseEntity<>(ans, HttpStatus.OK);
		}
		ans.put("message", "DP changed successfully");
		return new ResponseEntity<>(ans, HttpStatus.OK);
	}

	@Autowired
	Sentt sointRepo;

	@Autowired
	AWSClientConfigService aservice;

	AmazonS3 as3;

	@Autowired
	private ClarosNotificationRepo notificationRepository;

//	@Autowired
//	ClarosProducer clarosProducer;
//
//	@GetMapping("/tempSignal")
//	public String tempSignal() {
//		String roleName = "7wg.accounts.user1";
//		ClarosNotification notif = new ClarosNotification(null, "ManualFeedSignal", LocalDateTime.now() + "",
//				LocalDateTime.now(), "unread", false, roleName, "");
//		notificationRepository.save(notif);
//		Notification noti = new Notification(roleName);
//		clarosProducer.send3(noti);
//		return null;
//	}
	
//	@PostMapping("/addFinance")
//	public ResponseEntity<?> addFinance()
	

	@SuppressWarnings("deprecation")
	@PostMapping("/signal")
	public ResponseEntity<?> signal(@RequestHeader("Authorization") String token,
			@RequestParam(required = false) String address, @RequestParam(required = false) String description,
			@RequestParam(required = false) String type, @RequestParam(required = false) String typeofcrime,
			@RequestParam(required = false) List<String> poi, @RequestParam(required = false) List<MultipartFile> file,
			@RequestHeader(name = "formattedDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date formattedDate,
			@RequestHeader String roleName, @RequestParam(required = false) String gis,
			@RequestParam(required = false) String time) throws Exception {

		token = token.replace("Bearer ", "");
		System.out.println("POI: " + poi);
		ans = new HashMap<>();
		ans.put("message", "SuccessfullySaved");
		ans.put("dateSent", formattedDate);

		if (formattedDate == null) {
			System.out.println("dateNotRecieved");
			formattedDate = new Date();
		} else {
			if (time != null && time.length() > 0 && time.contains(":")) {
				String[] splitHM = time.split(":");
				int Hour = 0, Minute = 0;
				if (splitHM.length == 2) {
					Hour = Integer.parseInt(splitHM[0].trim());
					Minute = Integer.parseInt(splitHM[1].trim());
				}
				formattedDate.setHours(Hour);
				formattedDate.setMinutes(Minute);
				System.out.println("formattedDate: " + formattedDate.toString());
				ans.put("dateSent", formattedDate);
			}
		}

		long datel = formattedDate.getTime();

		Sntt a = new Sntt(null, address, description, type, typeofcrime, formattedDate.toString(), datel, null, poi);
		ans.put("dataSent", poi);

//		if(poi==null || poi.isEmpty()) return new ResponseEntity<>(ans,HttpStatus.OK);

		try {

			String id = sointRepo.save(a).getId();
			int i = 1;
//		if(i==1) token = token1;
			as3 = aservice.awsClientConfiguration(token);
			if (!as3.doesBucketExist("signaldms"))
				as3.createBucket("signaldms");

			ObjectMetadata ob = new ObjectMetadata();
			List<AttachmentFile> fileObjects = new ArrayList<>();
			i = 0;
			if (file != null)
				for (MultipartFile files : file) {
					i++;
					System.out.println("i: " + i + "fileName: " + files.getOriginalFilename());
					as3.putObject("signaldms", id + (i), files.getInputStream(), ob);
					fileObjects.add(
							new AttachmentFile(id + (i), "signaldms" + "/" + id + (i), files.getOriginalFilename()));
				}

			List<String> usernames = new ArrayList<>();
			if (poi != null) {
				List<Crime> crimeList = new ArrayList<>();
				GeoPoint location = new GeoPoint();
				if (gis != null) {
					String[] split = gis.split(",");
					System.out.println("GIS: " + split[0]);
					location.setLat(Double.parseDouble(split[0].trim()));
					location.setLon(Double.parseDouble(split[1].trim()));
					System.out.println(gis);
				}

				for (String useridPoi : poi) {
					String username = cUserRepo.findByUserid(useridPoi).get().getName();
					usernames.add(username);
					if(gis != null) 
					{
					System.out.println("crimeGIS");
                    Crime abvc= new Crime(location, useridPoi, username, null, formattedDate);
					crimeRepo.save(abvc);
					System.out.println("Gis: "+abvc);
					}
					
				}
			}
			Alerts b = new Alerts("High", "Manual Feed", description, formattedDate, formattedDate.toString(), datel,
					poi);
			b.setUsernames(usernames);
			b.setFileobjects(fileObjects);
			b.setType(type);
			b.setGis(gis);
			alertRepo.save(b);
			a.setUserids(poi);
			a.setUserNames(usernames);
			a.setFileobjects(fileObjects);
			a.setDate(formattedDate.toString());
			sointRepo.save(a);
			ClarosNotification notif = new ClarosNotification(null, description, LocalDateTime.now() + "",
					LocalDateTime.now(), "unread", false, roleName, "");
			notificationRepository.save(notif);
			ans.put("dataSaved", a);
			Notification noti = new Notification(roleName);
//			clarosProducer.send3(noti);
			ans.put("fileNo", i);
//		signalRepo.save(signal);
			return new ResponseEntity<>(ans, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			ans.put("message", "error" + e);
			return new ResponseEntity<>(ans, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/allSignal")
	public ResponseEntity<?> signalAll() {
		return new ResponseEntity<>(sointRepo.findAll(), HttpStatus.OK);
	}

	///

	public String getAlphaNumericString(int n) {

		// choose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}

	///
	@PostMapping("/updatePOI")
	public ResponseEntity<?> updatePOI(@RequestHeader String userid, @RequestParam(value = "name") String name,
			@RequestParam(value = "age", required = false) String age,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "information", required = false) Object information,
			@RequestParam(value = "monitored", required = false) boolean monitored,
			@RequestParam(value = "score", required = false) String score,

			HttpServletRequest request,
			@RequestHeader(name = "Dob", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "alias", required = false) String alias,
			@RequestParam(value = "height", required = false) Integer height,
			@RequestParam(value = "eyecolor", required = false) String eyecolor,
			@RequestParam(value = "idType", required = false) String idType,
			@RequestParam(value = "idNumber", required = false) String idNumber,
			@RequestParam(value = "identificationMark", required = false) String identificationMark,
			@RequestParam(value = "profession", required = false) String profession,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "mobNo", required = false) String mobNo,
			@RequestParam(value = "address", required = false) String address) {

		Optional<ClarosUsers> findById = cUserRepo.findByUserid(userid);
		if (!findById.isPresent())
			return new ResponseEntity("No data found with id : " + userid, HttpStatus.INTERNAL_SERVER_ERROR);

		if (findById.isPresent()) {
			ClarosUsers ClarosUsers = findById.get();
			ClarosUsers.setName(name.replace(" ", "_"));

//			updating information
			InformationDTO info = new InformationDTO();
			info.setEmail(email);
			info.setAlias(alias);
			info.setAddress(address);
			info.setEyecolor(eyecolor);
			info.setGender(gender);
			info.setHeight(height);
			info.setIdentificationMark(identificationMark);
			info.setIdNumber(idNumber);
			info.setIdType(idType);
			info.setMobileNo(mobNo);
			info.setProfession(profession);
			info.setName(name);
			if (dob != null) {
				long ansl = LocalDate.now().compareTo(dob);
				info.setAge(ansl);
				info.setDob(dob.toString());
				ClarosUsers.setDob(dob.toString());
			}
			info.setDescription(description);

			ClarosUsers.setInformation(info);
			cUserRepo.save(ClarosUsers);

		}

		return new ResponseEntity<>("Update Successfull", HttpStatus.OK);
	}

	@PostMapping("/registerPOI")
	public ResponseEntity<?> registerPOI(/* @RequestBody ClarosUsers data, */
			@RequestParam(value = "name") String name, @RequestParam(value = "userid", required = false) String userid,
			@RequestParam(value = "age", required = false) String age,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "information", required = false) Object information,
			@RequestParam(value = "monitored", required = false) boolean monitored,
			@RequestParam(value = "score", required = false) String score,
			@RequestHeader(name = "Dob", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
			@RequestParam(value = "img", required = false) MultipartFile imgFile,
			@RequestParam(value = "audio", required = false) MultipartFile audioFile, HttpServletRequest request,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "alias", required = false) String alias,
			@RequestParam(value = "height", required = false) Integer height,
			@RequestParam(value = "eyecolor", required = false) String eyecolor,
			@RequestParam(value = "idType", required = false) String idType,
			@RequestParam(value = "idNumber", required = false) String idNumber,
			@RequestParam(value = "identificationMark", required = false) String identificationMark,
			@RequestParam(value = "profession", required = false) String profession,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "mobNo", required = false) String mobNo,
			@RequestParam(value = "address", required = false) String address) throws IOException {

		System.out.println(dob);
		long ansl = LocalDate.now().compareTo(dob);
//		long ansl = 0L;

		InformationDTO info = new InformationDTO();
		info.setEmail(email);
		info.setAlias(alias);
		info.setAddress(address);
		info.setEyecolor(eyecolor);
		info.setGender(gender);
		info.setHeight(height);
		info.setIdentificationMark(identificationMark);
		info.setIdNumber(idNumber);
		info.setIdType(idType);
		info.setMobileNo(mobNo);
		info.setProfession(profession);
		info.setName(name);
		info.setAge(ansl);
		info.setDescription(description);

		ClarosUsers data = new ClarosUsers(name.replace(" ", "_"), userid);
		info.setDob(dob.toString());
		data.setDob(dob.toString());
		data.setInformation(info);

		if (imgFile != null && !imgFile.isEmpty())
			data.setImage(imgFile.getBytes());

		cUserRepo.save(data);

		System.out.println("RegisterPOI+: " + data);
		Optional<ClarosUsers> findById = null;
		do {
			userid = getAlphaNumericString(7);
			findById = cUserRepo.findById(userid);
		} while (findById.isPresent());

		if (name == null || name.length() == 0)
			return new ResponseEntity<>("Name cannot be Empty", HttpStatus.BAD_REQUEST);

		// img and audio
		if (description != null)
			description = "";

		uploadImage1(userid, description, imgFile, request);

		// upload audio
		uploadAudio1(userid, description, audioFile, request);

		data.setUserid(userid);
		data.setEnemyID(new ArrayList<>());
		data.setFriendsID(new ArrayList<>());
		data.setMonitoredBy(new ArrayList<>());
		cUserRepo.save(data);

		System.out.println("registerPOI: " + data);
		ans = new HashMap<>();
		ans.put("message", "Saved Successfully");
		ans.put("userid", getAlphaNumericString(7));
		ans.put("dataSent", data);
		return new ResponseEntity<>(ans, HttpStatus.OK);
	}

	@PostMapping("/addRelation")
	public ResponseEntity<?> addRelation(@RequestBody DTOaddRelation data, @RequestHeader String userid,
			@RequestHeader String userName) {
		System.out.println("AddRelation: " + data);

		String relationType = data.getRelationType();
		Boolean relation = data.getRelation();
		ClarosUsers userP = cUserRepo.findByUserid(userid).get();

		List<String> userids = new ArrayList<>();
		for (SusSearch user : data.getSelectedUser())
			userids.add(user.getUserid());

		if (relationType != null && relationType.length() > 0) {

			if (relation) {
				Set<String> friendsID = new HashSet<>( userP.getFriendsID());
				friendsID.addAll(userids);
				userP.setFriendsID(new ArrayList<>(friendsID));
				ClarosUsers usert = cUserRepo.findByUserid(userids.get(0)).get();
				friendsID = new HashSet<>(usert.getFriendsID());
				friendsID.add(userid);
				usert.setFriendsID(new ArrayList<>(friendsID));
				cUserRepo.save(userP);
				cUserRepo.save(usert);
			} else {
				Set<String> enemyID = new HashSet<>( userP.getEnemyID());
				enemyID.addAll(userids);
				userP.setEnemyID(new ArrayList<>(enemyID));
				ClarosUsers usert = cUserRepo.findByUserid(userids.get(0)).get();
				enemyID = new HashSet<>(usert.getFriendsID());
				enemyID.add(userid);
				usert.setEnemyID(new ArrayList<>(enemyID));
				cUserRepo.save(usert);
				cUserRepo.save(userP);
			}
		}

		ans = new HashMap<>();
		ans.put("message", "Success");
		ans.put("dataSent", data);
		return new ResponseEntity<>(ans, HttpStatus.OK);
	}

	public List<RadialTree> levelCheck(String parentId, String userid2, List<String> friendsListSet,
			Set<String> VisitedList, int level) {
		if (level >= 5) {
			return new ArrayList<>();
		}

		List<RadialTree> fans = new ArrayList<>();
		System.out.println("Level:" + level);

		for (String userid : friendsListSet) {
			if (VisitedList.contains(userid))
				continue;
			VisitedList.add(userid);
			Optional<ClarosUsers> user = cUserRepo.findByUserid(userid);
			if (!user.isPresent())
				continue;

			if (user.get().getFriendsID().contains(userid2)) {
				System.out.println("Found");
				fans.add(new RadialTree(user.get().getUserid(), user.get().getName().replace("_", " "),
						"" + user.get().getAge(), parentId, user.get().getImage(), "true"));
				Optional<ClarosUsers> user2 = cUserRepo.findByUserid(userid2);
				fans.add(new RadialTree(user2.get().getUserid(), user2.get().getName().replace("_", " "),
						"" + user2.get().getAge(), user.get().getUserid(), user2.get().getImage(), "false"));
				return fans;

			} else {
				List<RadialTree> levelCheckresp = levelCheck(userid, userid2, user.get().getFriendsID(), VisitedList,
						1 + level);
				if (levelCheckresp.size() > 0) {
					fans.add(new RadialTree(user.get().getUserid(), user.get().getName().replace("_", " "),
							"" + user.get().getAge(), parentId, user.get().getImage(), "true"));
					fans.addAll(levelCheckresp);
					return fans;
				}
			}

		}

		return new ArrayList<>();
	}

	@PostMapping("/searchRelation")
	public ResponseEntity<?> addRelationImage(@RequestHeader String userid, @RequestHeader String userName,
			@RequestBody DTOaddRelation body) {
		ans = new HashMap<>();

		Optional<ClarosUsers> user = cUserRepo.findByUserid(userid);
		String userid2 = body.getSelectedUser().get(0).getUserid();
		System.out.println("Userid2" + userid2);
		Optional<ClarosUsers> user2 = cUserRepo.findByUserid(userid2);
		ans = new HashMap<>();
		if (!user.isPresent() || !user2.isPresent()) {
			ans.put("message", "User Not Found");
			ans.put("error", "User Not Found");
			return new ResponseEntity<>(ans, HttpStatus.BAD_REQUEST);
		}
		List<RadialTree> fans = new ArrayList<>();
//		 levelCheck(String parentId, String userid2, List<String> friendsListSet,
//					Set<String> VisitedList, int level)
		List<RadialTree> levelCheckresp = levelCheck(null, userid2, Arrays.asList(userid), new HashSet<>(), 1);
		if (levelCheckresp.size() > 0) {
			fans.addAll(levelCheckresp);
			ans.put("radialTree", fans);
			return new ResponseEntity<>(ans, HttpStatus.OK);
		}
		fans.add(new RadialTree(user.get().getUserid(), user.get().getName().replace("_", " "),
				"" + user.get().getAge(), null, user.get().getImage(), "true"));



		fans.add(new RadialTree("1", "No Relation Found", "" + 0, user.get().getUserid(), null, "false"));
		fans.add(new RadialTree(user2.get().getUserid(), user2.get().getName().replace("_", " "),
				"" + user2.get().getAge(), "1", user2.get().getImage(), "false"));
		ans.put("radialTree", fans);
		return new ResponseEntity<>(ans, HttpStatus.OK);

	}

	@Autowired
	AlertRepo alertRepo;

	@GetMapping("/alertsClaros")
	public ResponseEntity<?> alertClaros(/* roleName */) {
		// crime user have Monitored field, ClarosUsers.monitored true
		List<ClarosUsers> monitoredUsers = cUserRepo.findAllByMonitored(true);
		System.out.println("monitored users : " + monitoredUsers.toString());
		List<String> lst = new ArrayList<>();
		for (ClarosUsers user : monitoredUsers) {
			lst.add(user.getUserid());
		}
		System.out.println("user ids : " + lst);
		// these userids needs to be in this findAllByUserid... in api below
		List<Alerts> findAllByUseridIn = alertRepo.findAllByUseridIn(lst);
		System.out.println("findAllByUseridIn : " + findAllByUseridIn);
		return new ResponseEntity<>(findAllByUseridIn, HttpStatus.OK);
	}

	@PostMapping("/alertsSearch")
	public ResponseEntity<?> alertSearch(
			@RequestHeader("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fdate,
			@RequestHeader("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date tdate,
			@RequestHeader("search") String search, @RequestHeader("priority") String priority,
			@RequestHeader(name = "userid", required = false) String userid) {
		System.out.println(fdate + ":" + tdate + " : " + new Date(tdate.getTime() + 1l * 24 * 60 * 60 * 1000));
		ans = new HashMap<>();

		BoolQueryBuilder suggestionbq = new BoolQueryBuilder();
		suggestionbq.filter((QueryBuilders.rangeQuery("ldate")
				.lte(new Date(tdate.getTime() + 1l * 24 * 60 * 60 * 1000).getTime()).gte(fdate.getTime())));
		if (priority != null && priority.length() > 0 && !priority.equals("All")) {
			BoolQueryBuilder suggestionName = new BoolQueryBuilder();
			priority = priority.trim();
			suggestionName.should(QueryBuilders.wildcardQuery("priority", "*" + priority + "*"));
			suggestionName.should(QueryBuilders.matchQuery("priority", priority));
			suggestionbq.must(suggestionName);
		}
		if (search != null && search.length() > 0) {
			BoolQueryBuilder suggestionName = new BoolQueryBuilder();
			search = search.trim();
			suggestionName.should(QueryBuilders.wildcardQuery("description", "*" + search + "*"));
			suggestionName.should(QueryBuilders.matchQuery("description", search));
			suggestionbq.must(suggestionName);
		}
		// later added
		if (userid == null || userid.length() == 0) {
			List<ClarosUsers> allByMonitored = cUserRepo.findAllByMonitored(true);
			BoolQueryBuilder suggestionName = new BoolQueryBuilder();
			for (ClarosUsers user : allByMonitored) {
//			lst.add(user.getUserid());
				suggestionName.should(QueryBuilders.matchQuery("userid", user.getUserid()));
			}
			suggestionbq.must(suggestionName);
		} else {
			suggestionbq.must(QueryBuilders.matchQuery("userid", userid));
		}

		EsRequest esRequest = new EsRequest().queryBuilder(suggestionbq).index("alerts_claros");
//		System.out.println(suggestionbq);
		List<Object> as = new ArrayList<>();
		try {
			EsResponse esResponse = elasticService.searchES(esRequest, 0, 1000);
			String tempval = null;
			System.out.println("----------" + esResponse.getTotalHits());
			for (org.elasticsearch.search.SearchHit esearch : esResponse.getHits()) {
//			String id, String priority, String sourceofalert, String description, Date date, String dispDate
				Map<String, Object> sourceMap = esearch.getSourceAsMap();
				Alerts a = new Alerts(esearch.getId(), (String) sourceMap.get("priority"),
						(String) sourceMap.get("sourceofalert"), (String) sourceMap.get("description"),
						sourceMap.get("date"), (String) sourceMap.get("dispDate"), null, (String) sourceMap.get("name"),
						(List<String>) sourceMap.get("userid"));
				a.setUsernames(sourceMap.get("usernames") == null ? new ArrayList<>()
						: (List<String>) sourceMap.get("usernames"));
                a.setGis(sourceMap.get("gis")==null ? null : (String) sourceMap.get("gis"));
                a.setType(sourceMap.get("type")==null ? null : (String) sourceMap.get("type"));
				as.add(a);
			}
		} catch (ElasticsearchStatusException e1) {
			System.out.println("errorAdvanceSearch" + e1);
			throw e1;
		} catch (IOException e1) {
			System.out.println("errorAdvanceSearchIO:" + e1);
			return null;
		}
//		return alertRepo.findAllByDescriptionContainingAndPriorityAndDateGreaterThanEqualAndDateLessThanEqual(search,
//				priority,  fdate, tdate);

		return new ResponseEntity<>(as, HttpStatus.OK);

	}


	@PostMapping("/addAlerts")
	public ResponseEntity<?> addAlerts(@RequestBody List<Alerts> data,
			@RequestHeader(required = false, name = "isDel") String isDel) {
		if (isDel != null && isDel.equals("yes"))
			alertRepo.deleteAll();

		for (Alerts a : data) {
			a.setDate(LocalDateTime.now());
			a.setDispDate("" + new Date());
			a.setLdate(new Date().getTime());
			alertRepo.save(a);
		}
		return new ResponseEntity<>(alertRepo.findAll(), HttpStatus.OK);
	}

	@GetMapping("/AlertsMonitorPOI")
	public ResponseEntity<?> addMonitorPOI(@RequestHeader(required = false) String userid,
			@RequestHeader(required = false) Boolean addorNot, @RequestHeader String roleName) {
		System.out.println("AlertsMonitorPOI" + userid);
		System.out.print("sdf" + addorNot);

		List<ClarosUsers> findAllByUserid = new ArrayList<>();
		if (userid != null && userid.length() > 0) {
			findAllByUserid = cUserRepo.findAllByUserid(userid);
			for (ClarosUsers a : findAllByUserid) {
				System.out.print(a.getUserid());
				if (addorNot)
					a.setMonitored(true);
				else
					a.setMonitored(false);
				cUserRepo.save(a);
				System.out.print(a.getMonitored());
			}
		}
		List<ClarosUsers> monitoredUser = cUserRepo.findAllByMonitored(true);
		for (ClarosUsers a : monitoredUser) {
			a.setName(a.getName().replace("_", " "));
		}
		ans = new HashMap<>();
		ans.put("data", monitoredUser);
		return new ResponseEntity<>(ans, HttpStatus.OK);
	}
//	@Autowired
//	EsearchProducer esearchProducer;
//	@Autowired
//	MinioService minioService;

	@PostMapping("/addPointData")
	public ResponseEntity<?> addPointData(@RequestParam(required = false) MultipartFile file,
			@RequestParam String userid, @RequestParam(required = false) String description,
			@RequestParam String type) {

		System.out.println("PointData: " + userid + ":" + file.getOriginalFilename());

		DTOPointData a = new DTOPointData(file.getOriginalFilename(), userid, type, description);
		ans = new HashMap<>();
		ans.put("message", "Services will resume soon");
		ans.put("dataSent", a);
		return new ResponseEntity<>(ans, HttpStatus.OK);
	}

	// MatchImage
	@PostMapping("/addFileFaceRecImage")
	public ResponseEntity<?> addFileFaceRecImage(@RequestParam MultipartFile img,
			@RequestHeader("matchingProbablity") int sliderPercentage, HttpServletRequest request) {
		ans.put("type", "image");
		System.out.println("addFileFaceRecImage");
//		unImageRepo.save(new UnclassifiedImage("rakesh"));
		UnclassifiedImage a = new UnclassifiedImage();
		a.setUploadid(request.getHeader("uploadedby"));
		a.setTime(LocalDateTime.now());
		unImageRepo.save(a);
		String uri = "http://" + faceService + "/claros/facerec/upload?upload_id=" + a.getId() + "&min_val="
				+ sliderPercentage;
		String[] split = img.getOriginalFilename().split(".");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.setContentLength(request.getContentLength());
		// Create the form data request body
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

		formData.add("img", img.getResource());
//		formData.add("upload_id", upload_id);

		// Create the request entity with form data
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

		// Make a POST request to the target API to forward the file
		ResponseEntity<String> targetResponse = new RestTemplate().postForEntity(uri, requestEntity, String.class);

		// Handle the response from the target API as needed
		return ResponseEntity.ok("Target API Response: " + targetResponse.getBody());

	}

	// MatchVideo
	@PostMapping("/addfaceRecProcess")
	public ResponseEntity<?> addfaceRecProcess(@RequestParam String video_id, HttpServletRequest request) {
		try {
			ans.put("type", "video");
			System.out.println("addVideoFaceRecProcess");
			String uri = "http://" + faceService + "/claros/facerec/upload_video_pro?doc_id=" + video_id;
			String token = (String) request.getHeader("Authorization").replace("Bearer", "").trim();

			as3 = aservice.awsClientConfiguration(token);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);

			Optional<UnclassifiedImage> findById = unImageRepo.findById(video_id);

			if (!findById.isPresent())
				return new ResponseEntity<>("NotFound", HttpStatus.BAD_REQUEST);

//			String path = findById.get().getVideo_path();
//			String[] split = path.split("/", 5);
			S3ObjectInputStream inputStream;
			try {

				HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
				headers.setContentType(MediaType.APPLICATION_JSON);
//			headers.setContentDispositionFormData("attachment", "vid" + ".mp4");
//			headers.setContentLength();
				// Create the form data request body
				MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

//			formData.add("video", new InputStreamResource(inputStream) );
				formData.add("doc_id", video_id);
				// Create the request entity with form data
				HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

				// Make a POST request to the target API to forward the file
				CompletableFuture.runAsync(() -> {
					try {

						ResponseEntity<String> targetResponse = new RestTemplate().postForEntity(uri, requestEntity,
								String.class);
						System.out.println("VideoProcess: " + video_id + targetResponse.getBody());
					} catch (Exception e) {
						e.printStackTrace();
					}

				});

				ans.put("message", "Sent for Processing");
//			 ans.put("targetResponse", targetResponse.getBody());
//			IOUtils.copy(inputStream, byteArrayOutputStream);
//			IOUtils.closeQuietly(bufferedOutputStream);
//			IOUtils.closeQuietly(byteArrayOutputStream);
//			
//			IOUtils.closeQuietly(inputStream);

			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.notFound().build();
			}

			return ResponseEntity.ok(ans);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error" + e, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/addFileFaceRecVideo")
	public ResponseEntity<?> addPointDatass(@RequestParam MultipartFile img,

			HttpServletRequest request) {

		try {

			UnclassifiedImage a = new UnclassifiedImage();
//			a.setUploadid(upload_id);
			a.setType("video");
			a.setFace_details(new ArrayList<>());

			unImageRepo.save(a);
			String id = a.getId();
			int i = 1;
			String token = (String) request.getHeader("Authorization").replace("Bearer", "").trim();

			as3 = aservice.awsClientConfiguration(token);
			if (!as3.doesBucketExist("fileclaros"))
				as3.createBucket("fileclaros");

			ObjectMetadata ob = new ObjectMetadata();
			ob.setContentLength(img.getSize());

			as3.putObject("fileclaros", id + ".mp4", img.getInputStream(), ob); // data length
			a.setVideo_path("fileclaros/" + id + ".mp4");
			a.setTime(LocalDateTime.now());

			unImageRepo.save(a);

			CompletableFuture.runAsync(() -> {
				try {
					addfaceRecProcess(id, request);

				} catch (Exception e) {
					e.printStackTrace();
				}

			});
			ans = new HashMap<>();
			ans.put("message", "SuccessfullySaved");
			ans.put("type", "video");
			ans.put("dataSaved", a);
			return new ResponseEntity<>(ans, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			ans.put("error", e);
			return new ResponseEntity<>(ans, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/addCrimeUser")
	public ResponseEntity<?> addCrimeUser(@RequestBody List<ClarosUsers> a) {
		for (ClarosUsers as : a) {
			cUserRepo.save(as);
		}
		return new ResponseEntity<>(cUserRepo.findAll(), HttpStatus.OK);
	}

	@PostMapping("/uploadCSV")
	public String handleFileUpload(@RequestParam("file") List<MultipartFile> files, @RequestHeader String type)
			throws ParseException {

		System.err.println("type" + type);
		for (MultipartFile file : files) {
			if (!type.equals("alert")) {
				if (!file.isEmpty()) {

					try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
						List<Crime> crimeList = new ArrayList<>();
						String line;
						while ((line = br.readLine()) != null) {
							// Process each line of the CSV file here
//                    System.out.println(line);
							String[] split = line.split(",");

							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

							// Parse the time string into a Date object
							Date date = sdf.parse(split[4]);

							GeoPoint location = new GeoPoint();
							location.setLat(Double.parseDouble(split[0]));
							location.setLon(Double.parseDouble(split[1]));
//                    GeoLocation geo=GeoLocation
							Crime crime = new Crime(location, split[2], split[3], split[4], date);
							crimeList.add(crime);
						}

						crimeRepo.saveAll(crimeList);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} // 'if' type end
			else {

				if (!file.isEmpty()) {

					try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
						List<Alerts> alertList = new ArrayList<>();
						String line;
						while ((line = br.readLine()) != null) {
							// Process each line of the CSV file here
//                    System.out.println(line);
							String[] split = line.split(",");

							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

							// Parse the time string into a Date object
							Date date = sdf.parse(split[5]);

							Alerts alert = new Alerts(split[0], split[1], split[2], split[3], split[4], split[5], date,
									date.getTime());
							alertList.add(alert);
						}

						alertRepo.saveAll(alertList);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} // end of else
		} // end of for loop
		return "data saved!";
	}

	@PostMapping("/uploadVideo")
	public ResponseEntity<?> uploadVideo(@RequestParam("img") MultipartFile file, HttpServletRequest request)
			throws AmazonServiceException, SdkClientException, IOException {
		Map<String, String> res = new HashMap<>();
		try {
			String token = (String) request.getHeader("Authorization").replace("Bearer", "").trim();
			as3 = aservice.awsClientConfiguration(token);
			if (!as3.doesBucketExistV2("claros")) {
				as3.createBucket("claros");
			}

			UUID uuid = UUID.randomUUID();
			System.err.println("filename" + file.getOriginalFilename());
			String[] split = file.getOriginalFilename().split("\\.");
			System.err.println("size" + split.length);
			ObjectMetadata meta = new ObjectMetadata();
//			meta.addUserMetadata("userId", userId);
			meta.setContentLength(file.getSize());
			String path = uuid + "." + split[split.length - 1];
			as3.putObject("claros", path, file.getInputStream(), meta);

			UserData user = new UserData();
			user.setFileUrl("http://3.7.32.64:9000/claros/" + path);
			user.setInProgress(true);
			user.setContent("This is some sample transcript for some video for Claros.");
			user.setTime(LocalDateTime.now());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
			user.setDisplayTime(LocalDateTime.now().format(formatter));
			user.setType("video");
//			user.setUser(userId);

			UserData save = userDataRepo.save(user);
			System.err.println("++++++++++++++");
			callPython("http://" + audioRec + "/claros/audio_process/audio_text?doc_id=" + save.getId());
			System.err.println("========");

			return new ResponseEntity<>("success!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			res.put("error", "Something went wrong. Please try again later.");
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/uploadAudio")
	public ResponseEntity<?> uploadAudio(@RequestParam("img") MultipartFile file,
			@RequestHeader("matchingProbablity") int sliderPercentage, HttpServletRequest request)
			throws AmazonServiceException, SdkClientException, IOException {
		Map<String, String> res = new HashMap<>();

		try {
			String token = (String) request.getHeader("Authorization").replace("Bearer", "").trim();
			as3 = aservice.awsClientConfiguration(token);
			if (!as3.doesBucketExistV2("claros")) {
				as3.createBucket("claros");
			}
			UUID uuid = UUID.randomUUID();
			String[] split = file.getOriginalFilename().split("\\.");
			ObjectMetadata meta = new ObjectMetadata();
//			meta.addUserMetadata("userId", userId);
			meta.setContentLength(file.getSize());
			String path = uuid + "." + split[split.length - 1];
			as3.putObject("claros", path, file.getInputStream(), meta);

			UserData user = new UserData();
			user.setFileUrl("http://3.7.32.64:9000/claros/" + path);
			user.setInProgress(true);
			user.setContent("");
			user.setTime(LocalDateTime.now());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
			user.setDisplayTime(LocalDateTime.now().format(formatter));
			user.setType("audio");
			user.setMinimumMatchPercentage(sliderPercentage);
			user.setMatchPercentage(new ArrayList<>());
//			user.setUser(userId);

			UserData save = userDataRepo.save(user);

			callPython("http://" + audioRec + "/claros/audio_process/audio_match?doc_id=" + save.getId());

			return new ResponseEntity<>("success!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			res.put("error", "Something went wrong. Please try again later.");
			return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/saveAudio")
	public ResponseEntity<?> uploadAudio1(@RequestHeader String userid,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam("img") MultipartFile file, HttpServletRequest request)
			throws AmazonServiceException, SdkClientException, IOException {
		Map<String, String> res = new HashMap<>();

		try {
			String token = (String) request.getHeader("Authorization").replace("Bearer", "").trim();
			as3 = aservice.awsClientConfiguration(token);
			if (!as3.doesBucketExistV2("claros")) {
				as3.createBucket("claros");
			}
			UUID uuid = UUID.randomUUID();
			String[] split = file.getOriginalFilename().split("\\.");
			ObjectMetadata meta = new ObjectMetadata();
//			meta.addUserMetadata("userId", userId);
			meta.setContentLength(file.getSize());
			String path = uuid + "." + split[split.length - 1];
			as3.putObject("claros", path, file.getInputStream(), meta);

			SaveAudio saveAudio = new SaveAudio();
			saveAudio.setFileUrl("http://3.7.32.64:9000/claros/" + path);
			saveAudio.setTime(LocalDateTime.now());
			saveAudio.setUserId(userid);
			saveAudio.setDescription(description);
			saveAudio.setUploadedBy(userid);
			saveAudio.setType("audio");
			saveAudio.setPossible_matches(new ArrayList<>());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
			saveAudio.setDisplayTime(LocalDateTime.now().format(formatter));
//			saveAudio.setAudio_encode(new java.util.Vector<>(192));

			SaveAudio save = saveAudioRepo.save(saveAudio);

			// python service
			callPython("http://" + audioRec + "/claros/audio_process/audio_sample?doc_id=" + save.getId());

			return new ResponseEntity<>("success!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/audio")
	public ResponseEntity<?> getAudio(@RequestHeader String userid) {
		List<SaveAudio> findAllByUserId = saveAudioRepo.findAllByUserId(userid);

		return new ResponseEntity(findAllByUserId, HttpStatus.OK);
	}

	@PostMapping("/saveImage")
	public ResponseEntity<?> uploadImage1(@RequestHeader String userid,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam("img") MultipartFile file, HttpServletRequest request)
			throws AmazonServiceException, SdkClientException, IOException {
		Map<String, String> res = new HashMap<>();

		try {
			String token = (String) request.getHeader("Authorization").replace("Bearer", "").trim();
			as3 = aservice.awsClientConfiguration(token);
			if (!as3.doesBucketExistV2("claros")) {
				as3.createBucket("claros");
			}

			// python service
			String uri = "http://" + faceService + "/claros/facerec/userimgadd?user_id=" + userid;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			headers.setContentLength(request.getContentLength());
			// Create the form data request body
			MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

			formData.add("img", file.getResource());
			formData.add("description", description); //
			formData.add("type", "image");
			formData.add("uploadedBy", userid);
//			formData.add("upload_id", upload_id);

			// Create the request entity with form data
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

			System.out.println("save image hit...........");
			// Make a POST request to the target API to forward the file
			ResponseEntity<?> targetResponse = new RestTemplate().postForEntity(uri, requestEntity, String.class);

			return new ResponseEntity<>(targetResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/image")
	public ResponseEntity<?> getImage(@RequestHeader String userid) {
		List<SaveImage> findAllByUserId = saveImageRepo.findAllByUserId(userid);

		return new ResponseEntity(findAllByUserId, HttpStatus.OK);
	}

	// Banty
	@GetMapping("/imgAaudio") // get all images and audio
	public ResponseEntity<?> getImgAaudio(@RequestHeader String userid) {
		List<SaveImage> findAllByUserId = saveImageRepo.findAllByUserId(userid);
		List<SaveAudio> findAllByUserId2 = saveAudioRepo.findAllByUserId(userid);

		List<Object> combinedList = new ArrayList<>();

		// Convert Iterable to Stream and collect elements into the combinedList
		combinedList.addAll(StreamSupport.stream(findAllByUserId.spliterator(), false).collect(Collectors.toList()));

		combinedList.addAll(StreamSupport.stream(findAllByUserId2.spliterator(), false).collect(Collectors.toList()));

		return new ResponseEntity(combinedList, HttpStatus.OK);
	}

	// Banty
	@GetMapping("/getImgAaudio")
	public ResponseEntity<?> getAllData2(@RequestHeader String id) {
		Optional<SaveImage> findById = saveImageRepo.findById(id);
		if (findById.isPresent()) {
			return new ResponseEntity(findById.get(), HttpStatus.OK);
		}
		Optional<SaveAudio> findById2 = saveAudioRepo.findById(id);
		if (findById2.isPresent()) {
			return new ResponseEntity(findById2.get(), HttpStatus.OK);
		}
		return ResponseEntity.internalServerError().body("error");
	}

	@GetMapping("/getAlertDetail")
	public ResponseEntity<?> getAlertDetail(@RequestHeader String userId) {
		Optional<Alerts> findById = alertRepo.findById(userId);
		return new ResponseEntity(findById.get(), HttpStatus.OK);
	}

	// Himanshu
	@GetMapping("/allData")
	public ResponseEntity<?> getAllData1() {
		List<UnclassifiedImage> unImg = unclassifiedImageRepo.findAll();

		List<AllDataDTO> allDataList = new ArrayList<>();

		// Process UnclassifiedImage data
		for (UnclassifiedImage img : unImg) {
			AllDataDTO allDataDTO = new AllDataDTO();
			allDataDTO.setId(img.getId());
			allDataDTO.setType(img.getType());
			if (img.getType() != null && img.getType().equals("image"))
				allDataDTO.setPath(img.getImg());
			else
				allDataDTO.setPath(img.getVideo_path());

			allDataDTO.setTime(img.getTime() != null ? img.getTime() : LocalDateTime.now());

			List<Object> faceDetails = img.getFace_details();
			if (faceDetails != null) {

				HashMap<String, Object> possibleMatchScores = new HashMap<>();
				Set<NameDTO> nameMatcher = new HashSet<>();
				for (Object face : faceDetails) {
					Map<String, Object> fd = (Map<String, Object>) face;
					possibleMatchScores
							.putAll((Map<String, Object>) fd.getOrDefault("possible_match_score", new HashMap<>()));

					NameDTO matches = new NameDTO();
//					for(String id: fd.keySet()) {						

					HashMap<String, Integer> hash = (HashMap<String, Integer>) fd.get("possible_match_score");

					for (String matchKey : hash.keySet()) {
						Optional<ClarosUsers> byId = cUserRepo.findByUserid(matchKey);
						if (byId.isPresent()) {
							String name = byId.get().getName();
							matches.setName(name);

							matches.setUserid(matchKey);
//								System.err.println("%% "+hash.get(matchKey));
							matches.setMatchPercentage(hash.get(matchKey));
							nameMatcher.add(matches);
						}

					}

//						matches.setMatchPercentage((Integer.parseInt(fd.get("possible_match_score")));
//					}
//                    
					List<NameDTO> collect = nameMatcher.stream().collect(Collectors.toList());
					allDataDTO.setNameDto(collect);

					System.out.println("name matcher = " + nameMatcher.toString());
				}

				allDataDTO.setPossible_match_score(possibleMatchScores);
			}

			allDataList.add(allDataDTO);

		}

		// Process UserData audio
		List<UserData> userDataList = StreamSupport.stream(userDataRepo.findAll().spliterator(), false)
				.collect(Collectors.toList());
		Set<NameDTO> nameMatcher = new HashSet<>();
		for (UserData user : userDataList) {
			AllDataDTO allDataDTO = new AllDataDTO();
			allDataDTO.setId(user.getId());
			allDataDTO.setType(user.getType());
			allDataDTO.setPath(user.getFileUrl());
			allDataDTO.setTime(user.getTime() != null ? user.getTime() : LocalDateTime.now());

			HashMap<String, Object> possibleMatchScores = new HashMap<>();
//	        List<NameDTO> nameMatcher = new ArrayList<>();
			for (UserAudioMatch match : user.getMatchPercentage()) {
				possibleMatchScores.put(match.getUserId(), match.getMatch_score());
				NameDTO nameDTO = new NameDTO();
				nameDTO.setMatchPercentage((int) match.getMatch_score());
				nameDTO.setName(match.getName());
				nameDTO.setUserid(match.getUserId());

				nameMatcher.add(nameDTO);

			}
			allDataDTO.setPossible_match_score(possibleMatchScores);

			System.out.println("name matchers... " + nameMatcher);
//	         List<NameDTO> nameDtos = allDataDTO.getNameDtos();
//	         System.out.println("name matchers from image ... "+ nameDtos);
//	         nameDtos.addAll(nameMatcher);
			List<NameDTO> collect = nameMatcher.stream().collect(Collectors.toList());
			allDataDTO.setNameDto(collect);

			allDataList.add(allDataDTO);
		}

		if (allDataList.isEmpty()) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
		}

		return new ResponseEntity<>(allDataList, HttpStatus.OK);
	}

	@Async
	private void callPython(String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// Create the form data request body
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
		String uri = url;
		CompletableFuture.runAsync(() -> {
			try {
				HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

				// Make a POST request to the target API to forward the file
				new RestTemplate().postForEntity(uri, requestEntity, String.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

		});

	}

	@GetMapping("/getAudioData")
	public ResponseEntity<?> getAudioData(@RequestHeader String Authorization) {
//		return new ResponseEntity<>(userDataRepo.findAllByUserAndInProgressAndType(userId,false,"audio"), HttpStatus.OK);
		List<UserData> findAllByInProgressAndType = userDataRepo.findAllByInProgressAndType(false, "audio");
		for (UserData a : findAllByInProgressAndType) {
			// tobeDeleted after Buckets are private
			String video_path = a.getFileUrl();
			if (video_path != null && !video_path.contains("http")) {
				video_path = "http://http://3.7.32.64:9000/" + video_path;
			}
			a.setFileUrl(video_path);
			//
		}
		return new ResponseEntity<>(findAllByInProgressAndType, HttpStatus.OK);

	}

	@GetMapping("/getVideoData")
	public ResponseEntity<?> getVideoData(@RequestHeader String Authorization) {
//		return new ResponseEntity<>(userDataRepo.findAllByUserAndInProgressAndType(userId,false,"video"), HttpStatus.OK);
		List<UserData> findAllByInProgressAndType = userDataRepo.findAllByInProgressAndType(false, "video");
		for (UserData a : findAllByInProgressAndType) {
			// tobeDeleted after Buckets are private
			String video_path = a.getFileUrl();
			if (video_path != null && !video_path.contains("http")) {
				video_path = "http://http://3.7.32.64:9000/" + video_path;
			}
			a.setFileUrl(video_path);
			//
		}
		return new ResponseEntity<>(userDataRepo.findAllByInProgressAndType(false, "video"), HttpStatus.OK);

	}

	@GetMapping("/getTxtFile")
	public ResponseEntity<?> getTxtFile(@RequestHeader("fileId") String fileId) {
		Map<String, String> res = new HashMap<>();
		Optional<UserData> file = userDataRepo.findById(fileId);
		if (file.isPresent()) {
			String content = file.get().getContent();
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("transcript.txt"))) {
				writer.write(content);
			} catch (IOException e) {
				e.printStackTrace();
				// Handle file writing error
				return ResponseEntity.badRequest().body(null);
			}
			// Read the file and create a ByteArrayResource
			ByteArrayResource resource = new ByteArrayResource(content.getBytes());

			// Prepare the HTTP headers for download
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transcript.txt");

			// Return the file as a downloadable resource
			return ResponseEntity.ok().headers(headers).contentLength(resource.contentLength())
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
		}
		res.put("error", "File not fond with given id!");
		return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
	}

	// download zip file
		@GetMapping("/download")
		public ResponseEntity<?> downloadZip(@RequestHeader String id, @RequestHeader("Authorization") String token)
				throws IOException {

			Optional<Alerts> byId = alertRepo.findById(id);
			if (!byId.isPresent()) {
				throw new NullPointerException("id : " + id + "not present");
			}
			Alerts alert = byId.get();
			token = token.replace("Bearer ", "");
			as3 = aservice.awsClientConfiguration(token);

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
			ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

			try {
				System.out.println("inside fileObject download");
				if (alert.getFileobjects() != null || alert.getFileobjects().size() != 0) {
					for (AttachmentFile file : alert.getFileobjects()) {
						String url = file.getPath();
						S3Object fil = as3.getObject("signaldms", file.getId());
						InputStream is = fil.getObjectContent();
						System.out.println(file.getFilename());
						File file2 = new File(file.getFilename());
						FileUtils.copyInputStreamToFile(is, file2);
						zipOutputStream.putNextEntry(new ZipEntry(file.getFilename()));
						FileInputStream fileInputStream = new FileInputStream(file2);
						IOUtils.copy(fileInputStream, zipOutputStream);
						fileInputStream.close();
						is.close();
						zipOutputStream.closeEntry();
					}
				}

				// Read about it
				if (zipOutputStream != null) {
					try {
						zipOutputStream.finish();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						zipOutputStream.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
					IOUtils.closeQuietly(zipOutputStream);
				}
				IOUtils.closeQuietly(bufferedOutputStream);
				IOUtils.closeQuietly(byteArrayOutputStream);

				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.set("Content-Disposition",
						"attachment; filename=\"" + LocalDateTime.now().toString() + ".zip" + "\"");

				return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(byteArrayOutputStream.toByteArray());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseEntity<>("Error occured", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	
}