package com.tempKafka.dto;

public class ImageDTO {

	Object image;
	String imageId;

	public ImageDTO() {
		super();
	}

	public ImageDTO(Object image, String imageId) {
		super();
		this.image = image;
		this.imageId = imageId;
	}

	public Object getImage() {
		return image;
	}

	public void setImage(Object image) {
		this.image = image;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	@Override
	public String toString() {
		return "ImageDTO [image=" + image + ", imageId=" + imageId + "]";
	}

}
