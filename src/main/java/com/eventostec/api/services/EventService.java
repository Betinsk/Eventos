package com.eventostec.api.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.eventostec.api.domain.Event;
import com.eventostec.api.domain.DTO.EventRequestDTO;
import com.eventostec.api.repositories.EventRepository;

@Service
public class EventService {
	
	@Autowired
	private AmazonS3 s3Client;
	
	@Value("${aws.bucket.name}")
	private String bucketName;

	@Autowired
	private EventRepository eventRepository;
	
	public Event createEvent(EventRequestDTO data) {
		String imgUrl = null;
		
		if(data.image() != null) {
			imgUrl = this.uploadImg(data.image());
		}
		
		Event newEvent = new Event();
		

		
		newEvent.setTitle(data.title());
		newEvent.setDescription(data.description());
		newEvent.setEventUrl(data.eventUrl());
		  newEvent.setDate(new Date(data.date()));
		newEvent.setRemote(data.remote());
		newEvent.setImgUrl(imgUrl);
		newEvent.setRemote(data.remote());
		
		eventRepository.save(newEvent);
		
		return newEvent;
	
	}


	private String uploadImg(MultipartFile multipartFile) {
		String filename = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
		
		try {
			File file = this.convertMultipartToFile(multipartFile);
			s3Client.putObject(bucketName, filename, file);
			file.delete();
			return s3Client.getUrl(bucketName, filename).toString();
		} catch (Exception e) {
			System.out.println("Erro ao subir arquivo");
			return null;
		}
 		
	}

	
	private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
		File convFile = new File(multipartFile.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(multipartFile.getBytes());
		fos.close();
		return convFile;
		
	}
	
}
