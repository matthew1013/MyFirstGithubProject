package com.anyonehelps.common.util;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.jets3t.service.S3Service;
import org.jets3t.service.ServiceException;
import org.jets3t.service.acl.AccessControlList;
import org.jets3t.service.acl.GroupGrantee;
import org.jets3t.service.acl.Permission;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;


public class AmazonS3Samples {
	private String awsAccessKey = "AKIAJXFFWEZTJZ3ZUQ2A";
	private String awsSecretKey = "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";
	
	
	public String getAwsAccessKey() {
		return awsAccessKey;
	}


	public void setAwsAccessKey(String awsAccessKey) {
		this.awsAccessKey = awsAccessKey;
	}


	public String getAwsSecretKey() {
		return awsSecretKey;
	}


	public void setAwsSecretKey(String awsSecretKey) {
		this.awsSecretKey = awsSecretKey;
	}
	
	public void pushFile(String key,File file) throws IOException, ServiceException, NoSuchAlgorithmException{
		AWSCredentials awsCredentials = new AWSCredentials(awsAccessKey, awsSecretKey);
		S3Service s3Service = new RestS3Service(awsCredentials);
		S3Bucket testBucket = s3Service.getBucket("anyonehelps");
		AccessControlList bucketAcl = s3Service.getBucketAcl(testBucket);
		bucketAcl.grantPermission(GroupGrantee.ALL_USERS, Permission.PERMISSION_READ);
		testBucket.setAcl(bucketAcl);
		s3Service.putBucketAcl(testBucket);
			
		// Create an empty object with a key/name, and print the object's details.
		S3Object object = new S3Object(file.getAbsoluteFile());
		object.setKey(key);
		object.setAcl(bucketAcl);
		System.out.println("S3Object before upload: " + object);
		// Upload the object to our test bucket in S3.
		object = s3Service.putObject(testBucket, object);
		// Print the details about the uploaded object, which contains more information.
		System.out.println("S3Object after upload: " + object);
	}

	public static void main(String[] args) throws IOException, ParseException {
		AmazonS3Samples amazonS3Samples = new AmazonS3Samples();
		
		File f = new File("c:\\anyonehelps.log");
		try {
			amazonS3Samples.pushFile("test/test1.log", f);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}