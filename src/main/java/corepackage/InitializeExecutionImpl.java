package corepackage;

import static frameworkcore.frameworkutils.Constants.*;

import java.io.IOException;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import frameworkcore.excelreader.ExcelDataReaderImpl;

public class InitializeExecutionImpl {

	private static Logger logger = LoggerFactory.getLogger(InitializeExecutionImpl.class);

	public void InitializeTestExecution() throws IOException {

		PropertyConfigurator.configure(LOG4JCONFPATH);
		logger.info("Executing Tests" + "  --  " + Thread.currentThread().getId());
		ExcelDataReaderImpl datareader = new ExcelDataReaderImpl(INPUTDATAEXCELPATH);
		datareader.ReadExcelInputData();

		RunWithTestNGImpl testNGRunner = new RunWithTestNGImpl();
		testNGRunner.ExecuteTests();
	}
}
