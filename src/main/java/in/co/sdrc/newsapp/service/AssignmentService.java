package in.co.sdrc.newsapp.service;

import java.text.ParseException;
import java.util.List;

import in.co.sdrc.newsapp.domain.Assignment;
import in.co.sdrc.newsapp.model.AssignModelForInfo;
import in.co.sdrc.newsapp.model.AssignmentModel;

public interface AssignmentService {

	Assignment create(AssignmentModel itemTransationmModel) throws ParseException;

	List<AssignModelForInfo> getByPerson(Integer id);

	AssignModelForInfo getByItem(Long id);

	Assignment submit(Long itemId, String returnDate);

	List<AssignModelForInfo> historyByPerson(Integer id);

	List<AssignModelForInfo> historyByItem(Long id);

	void logLatLong(String time, String latLong);

}