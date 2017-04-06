package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.Ticket;


public interface TicketService {

	public ResponseObject<Object> addTicket(Ticket ticket) throws ServiceException;
	
}
