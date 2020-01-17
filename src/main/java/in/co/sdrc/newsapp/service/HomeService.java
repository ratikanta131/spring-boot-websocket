package in.co.sdrc.newsapp.service;

import org.springframework.core.io.Resource;

public interface HomeService {
    Resource loadFileAsResource(String fileName);
}
