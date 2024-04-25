package com.tempKafka.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.tempKafka.model.NameDTO;

public class AllDataDTO {

    private String id;
    private HashMap<String, Object> possible_match_score;
    private String type;
    private String name;
    private List<NameDTO> nameDto;
    

    @Override
	public String toString() {
		return "AllDataDTO [id=" + id + ", possible_match_score=" + possible_match_score + ", type=" + type + ", name="
				+ name + ", time=" + time + ", path=" + path + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, Object> getPossible_match_score() {
        return possible_match_score;
    }

    public void setPossible_match_score(HashMap<String, Object> possible_match_score) {
        this.possible_match_score = possible_match_score;
    }
    

    public List<NameDTO> getNameDto() {
		return nameDto;
	}

	public void setNameDto(List<NameDTO> nameDto) {
		this.nameDto = nameDto;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Object getPath() {
        return path;
    }

    public void setPath(Object path) {
        this.path = path;
    }
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime time;
    private Object path;
}
