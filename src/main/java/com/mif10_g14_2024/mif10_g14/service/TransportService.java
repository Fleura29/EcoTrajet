package com.mif10_g14_2024.mif10_g14.service;

import com.mif10_g14_2024.mif10_g14.dao.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransportService {
    @Autowired
    TransportRepository transportRepository;


}
