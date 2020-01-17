package in.co.sdrc.newsapp.service;

public interface ConfigurationService {

	String createOneAutherity(String name, String description);

	String createOneDesignation(String name, String code);

	String createOneDesignationAutherityMapping(String designationCode, String autherityName);

}