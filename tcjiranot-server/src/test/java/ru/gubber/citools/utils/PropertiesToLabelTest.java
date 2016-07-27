package ru.gubber.citools.utils;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.List;
import java.util.Properties;

/**
 * Created by gubber on 21.10.2015.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class PropertiesToLabelTest extends TestCase{

	@Test
	public void simpleLabelTest(){
		PropertiesToLabelEvaluator evaluator = new PropertiesToLabelEvaluator();
		Properties props = new Properties();
		String simpleLabel = "SUCCESS";

		String resultLabel = evaluator.evaluateLabelFromProperties(simpleLabel, props);

		assertEquals(simpleLabel, resultLabel);
	}

	@Test
	public void getFormatStringAndPropsNamesFromExpressionTest(){
		PropertiesToLabelEvaluator evaluator = new PropertiesToLabelEvaluator();
		String expression = "inRights/{app.version}-{app.branch}#{app.buildnumber}";
		List<String> result = evaluator.getTemplateFromExpression(expression);
		assertEquals("inRights/%s-%s#%s", result.get(0));
		assertEquals("app.version", result.get(1));
		assertEquals("app.branch", result.get(2));
		assertEquals("app.buildnumber", result.get(3));
	}

	@Test
	public void evaluationLabelTest(){
		PropertiesToLabelEvaluator evaluator = new PropertiesToLabelEvaluator();
		Properties props = new Properties();
		props.put("app.version", "2.4.1");
		props.put("app.branch", "dev");
		props.put("app.buildnumber", "456");
		String simpleLabel = "inRights/{app.version}-{app.branch}#{app.buildnumber}";

		String resultLabel = evaluator.evaluateLabelFromProperties(simpleLabel, props);

		assertEquals("inRights/2.4.1-dev#456", resultLabel);
	}
}