package in.co.sdrc.newsapp.service;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import in.co.sdrc.newsapp.exception.LogFileNotFoundException;
import in.co.sdrc.newsapp.util.Constants;

@Service
public class HomeServiceImpl implements HomeService {


    @Autowired
	private ConfigurableEnvironment configurableEnvironment;


    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Resource resource = new UrlResource("file://" + configurableEnvironment.getProperty(Constants.LOG_DIRECTORY, null, null) + fileName);
            if(resource.exists()) {
                return resource;
            } else {
                throw new LogFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            System.out.println(ex);
            throw new LogFileNotFoundException("File not found " + fileName);
        }
    }

}