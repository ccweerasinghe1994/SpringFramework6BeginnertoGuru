package com.wchamara.spring6restmvc.service;

import com.wchamara.spring6restmvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {
    List<BeerCSVRecord> convertCsv(File file);
}
