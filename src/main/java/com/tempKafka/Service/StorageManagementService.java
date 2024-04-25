package com.tempKafka.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.BucketVersioningConfiguration;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3VersionSummary;
import com.amazonaws.services.s3.model.SetBucketVersioningConfigurationRequest;
import com.amazonaws.services.s3.model.VersionListing;




@Service
public class StorageManagementService {

	@Autowired
	private AWSClientConfig awsClientConfig;

	//======================================================================================================


	public ResponseEntity<Object> uploadFile(MultipartFile file, String username,String token) {
		try
		{
			AmazonS3 s3Client = awsClientConfig.awsClientConfiguration(token);

			File mFile = new File(file.getOriginalFilename());
			FileOutputStream os = new FileOutputStream(mFile);
			os.write(file.getBytes());

			String bucketName = "test" + username + "dms";
			bucketName = bucketName.replace(".", "");
			boolean check = s3Client.doesBucketExistV2(bucketName);
			if (check != true) {
				s3Client.createBucket(bucketName);
				BucketVersioningConfiguration configuration = new BucketVersioningConfiguration().withStatus("Enabled");
				SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest = new SetBucketVersioningConfigurationRequest(
						username, configuration);
				s3Client.setBucketVersioningConfiguration(setBucketVersioningConfigurationRequest);
				BucketVersioningConfiguration conf = s3Client.getBucketVersioningConfiguration(username);
				System.out.println("bucket versioning configuration status:    " + conf.getStatus());
			}
			s3Client.putObject(bucketName,file.getOriginalFilename(),mFile);
			os.close();
			return ResponseEntity.ok().body("File Uploaded successfully " + file.getOriginalFilename());
		}catch (Exception e) {
			return null;
		}
	}

	public boolean upload(MultipartFile file, String username, String token, String docId, String type) {
		InputStream inputStream = null;
		ObjectMetadata metadata= new ObjectMetadata();
		try
		{
			AmazonS3 s3Client = awsClientConfig.awsClientConfiguration(token);
			username = username.replace(".", "").toLowerCase();
			String bucketName = "test" + username + "dms";
//			String bucketName = username; //UNCOMMENT if testing is over for server and COMMENT ABOVE
			boolean check = s3Client.doesBucketExistV2(bucketName);
			if (check != true) {
				s3Client.createBucket(bucketName);
				BucketVersioningConfiguration configuration = new BucketVersioningConfiguration().withStatus("Enabled");
				SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest = new SetBucketVersioningConfigurationRequest(
						bucketName, configuration);
				s3Client.setBucketVersioningConfiguration(setBucketVersioningConfigurationRequest);
				BucketVersioningConfiguration conf = s3Client.getBucketVersioningConfiguration(bucketName);
				System.out.println("bucket versioning configuration status:    " + conf.getStatus());
			}

//			File mFile = new File("xwq"); 
//			FileOutputStream os = new FileOutputStream(mFile);
//			os.write(file.getBytes());
			inputStream = file.getInputStream();
//			s3Client.putObject(bucketName,docId,mFile);
			System.err.println("name is "+file.getOriginalFilename());
			metadata.addUserMetadata("fileSize", String.valueOf(file.getSize()));
			metadata.setContentLength(file.getSize());
			s3Client.putObject(bucketName, docId, inputStream, metadata);
//			os.close();
			inputStream.close();
			return true;
		}catch (Exception e) {e.printStackTrace();
			return false;

		}

	}

	public void deleteFile(String token, String username, String fileName) {

		try
		{
			AmazonS3 s3Client = awsClientConfig.awsClientConfiguration(token);

			String bucketName = "test" + username + "dms";
			bucketName = bucketName.replace(".", "");
//			String bucketName = username; //UNCOMMENT if testing is over for server and COMMENT ABOVE
			boolean check = s3Client.doesBucketExistV2(bucketName);
			if (check) {
				s3Client.deleteObject(bucketName, fileName);
			}
			else {
				return ;
			}


		}catch (Exception e) {
			return;
		}
	}

	public void copyFile(String username, String file, String newFile, String token) {
		try
		{
			String bucketName = "test" + username + "dms";
			bucketName = bucketName.replace(".", "");
//			String bucketName = username; //UNCOMMENT if testing is over for server and COMMENT ABOVE
			CopyObjectRequest copyObjRequest = new CopyObjectRequest(bucketName, file, bucketName, newFile);
			AmazonS3 s3Client = awsClientConfig.awsClientConfiguration(token);
			s3Client.copyObject(copyObjRequest);
		}
		catch (Exception e)
		{
			return;
		}

	}


	public S3ObjectInputStream downloadDoc(String bucketName,String key, String token,Long rangeStart) {
		AmazonS3Client s3Client;
		try {
//			String bucketName = username; //UNCOMMENT if testing is over for server and COMMENT ABOVE
			s3Client = (AmazonS3Client) awsClientConfig.awsClientConfiguration(token);
			// Start from the beginning
	        long rangeEnd = (8 + (rangeStart/1000000)) * 1000000; // 10 seconds * 1,000,000 bytes per second (assuming 1 Mbps)
	        ObjectMetadata objectMetadata = s3Client.getObjectMetadata(new GetObjectMetadataRequest(bucketName, key));
	        long fileSize = objectMetadata.getContentLength();
            System.err.println("fileSize"+fileSize);
	        // Set rangeEnd to file size
	        if(rangeEnd>=fileSize) {rangeEnd = fileSize - 1; System.out.println("exceededRange");}
	        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key)
	                .withRange(0, rangeEnd);
	        return s3Client.getObject(getObjectRequest).getObjectContent();
//			return s3Client.getObject(bucketName, key).getObjectContent();
		}catch (Exception e) {
			return null;
		}
	}

	public List<HashMap<String, Object>> versionDoc( String username, String filePath, String token, String fileName) {
		try {
			String bucketName = "test" + username + "dms";
			bucketName = bucketName.replace(".", "");
//			String bucketName = username; //UNCOMMENT if testing is over for server and COMMENT ABOVE
			AmazonS3Client s3Client = (AmazonS3Client) awsClientConfig.awsClientConfiguration(token);
			VersionListing listVersions = s3Client.listVersions(bucketName, filePath);
			List<S3VersionSummary> versionSummaries = listVersions.getVersionSummaries();
			List<HashMap<String, Object>> versionids = new ArrayList<>();

			int i = versionSummaries.size();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
			for (S3VersionSummary ver : versionSummaries) {
				System.err.println(ver.getVersionId());

				HashMap<String, Object> version = new HashMap<>();
				version.put("versionId", ver.getVersionId());

				long size = ver.getSize() / 1024;
				if(size > 1024)
				{
					size /= 1024;
					if(size > 1024)
					{
						size /= 1024;
						version.put("displaySize", size + " GB");
					}
					else
					{
						version.put("displaySize", size + " MB");
					}
				}
				else
				{
					version.put("displaySize", size + " KB");
				}


				version.put("fileName", fileName);
				version.put("size", ver.getSize());
				version.put("dateModified", formatter.format(ver.getLastModified()));
				version.put("versionCount", "#"+ i);
				version.put("isLatest", ver.isLatest());
				versionids.add(version);
				--i;
			}
			return versionids;

		}catch (Exception e) {
			return null;
		}
	}

	//get Persigned url for file with (token)
	public String getShareLink(String username, String role, String path, String token) {
		try {
			String bucketName = "test" + username + "dms";
			bucketName = bucketName.replace(".", "");
//			String bucketName = username; //UNCOMMENT if testing is over for server and COMMENT ABOVE
			AmazonS3Client s3Client = (AmazonS3Client) awsClientConfig.awsClientConfiguration(token);
			Date expri= new Date();
			long expTimeMillis = Instant.now().toEpochMilli();
			expTimeMillis += 1000 * 60 * 60 * 24;
			expri.setTime(expTimeMillis);

			GeneratePresignedUrlRequest purl = new GeneratePresignedUrlRequest(bucketName,path)
					.withExpiration(expri);
			URL url = s3Client.generatePresignedUrl(purl);

			return url.toString();
		}
		catch (Exception e) {
			return null;
		}
	}




	public S3ObjectInputStream downloadWithBucket( String filePath, String bucketName, String token) {
		try {
			AmazonS3Client s3Client = (AmazonS3Client) awsClientConfig.awsClientConfiguration(token);
			return s3Client.getObject(bucketName, filePath).getObjectContent();
		}catch (Exception e) {
			return null;
		}

	}

	public void deleteBucket( String username, String token)
	{
		try
		{
			AmazonS3Client s3Client =(AmazonS3Client) awsClientConfig.awsClientConfiguration(token);

			String bucketName = "test" + username + "dms";
			bucketName = bucketName.replace(".", "");
//			String bucketName = username; //UNCOMMENT if testing is over for server and COMMENT ABOVE
			boolean check = s3Client.doesBucketExistV2(bucketName);
			if (check) {
				s3Client.deleteBucket(bucketName);
			}
			else {
				return;
			}


		}catch (Exception e) {
			return;
		}
	}


	public S3ObjectInputStream getFileNow(String filePath,String bucketName,String token) {
		AmazonS3Client s3Client;
		try {
//			String bucketName = "test" + username + "dms";
//			bucketName = bucketName.replace(".", "");
//			String bucketName = username; //UNCOMMENT if testing is over for server and COMMENT ABOVE
			s3Client = (AmazonS3Client) awsClientConfig.awsClientConfiguration(token);
			return s3Client.getObject(bucketName, filePath).getObjectContent();
		}catch (Exception e) {
			return null;
		}


	}

	public S3ObjectInputStream downloadVersionDoc( String filePath, String username, String versionId, String token) {
		AmazonS3Client s3Client;
		try {
			String bucketName = "test" + username + "dms";
			bucketName = bucketName.replace(".", "");
//			String bucketName = username; //UNCOMMENT if testing is over for server and COMMENT ABOVE
			s3Client = (AmazonS3Client) awsClientConfig.awsClientConfiguration(token);

			GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName , filePath, versionId);
			return s3Client.getObject(getObjectRequest).getObjectContent();
		}
		catch (Exception e)
		{
			return null;
		}
	}

}
