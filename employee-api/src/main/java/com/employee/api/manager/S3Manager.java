package com.employee.api.manager;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.employee.api.config.S3Props;

@Service
public class S3Manager {

	@Autowired
	private S3Props s3Props;

	private AmazonS3 s3client;

	@PostConstruct
	private void init() {
		AWSCredentials credentials = new BasicAWSCredentials(s3Props.getAccessKey(), s3Props.getSecretKey());

		s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.DEFAULT_REGION).build();

		if (!s3client.doesBucketExist(s3Props.getBucketName())) {
			s3client.createBucket(s3Props.getBucketName());
		}
	}

	public void uploadFile(String fileName, InputStream input, ObjectMetadata metadata) {
		s3client.putObject(s3Props.getBucketName(), fileName, input, metadata);
	}

	public byte[] downloadFile(String fileName) throws IOException {
		S3Object s3object = s3client.getObject(s3Props.getBucketName(), fileName);
		S3ObjectInputStream inputStream = s3object.getObjectContent();
		return IOUtils.toByteArray(inputStream);
	}

	public ObjectMetadata createMetaData(MultipartFile file) {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(file.getContentType());
		metadata.setContentLength(file.getSize());
		return metadata;
	}
}
