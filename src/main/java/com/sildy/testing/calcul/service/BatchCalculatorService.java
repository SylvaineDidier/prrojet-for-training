package com.sildy.testing.calcul.service;

import java.util.List;
import java.util.stream.Stream;

import com.sildy.testing.calcul.domain.model.CalculationModel;

public interface BatchCalculatorService {
	public List<CalculationModel> batchCalculate(Stream<String> operations);
}
