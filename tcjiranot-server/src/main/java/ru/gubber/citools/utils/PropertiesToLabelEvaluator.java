package ru.gubber.citools.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gubber on 21.10.2015.
 */
public class PropertiesToLabelEvaluator {

	private final static Pattern VALUE_PLACE_HOLDER_PATTERN = Pattern.compile("\\{([\\w\\.]+)\\}");

	public String evaluateLabelFromProperties(String pattern, Properties props) {
		String result;
		List<String> formatStringAndPropNames = getTemplateFromExpression(pattern);
		String[] propsValues = new String[formatStringAndPropNames.size()-1];
		for(int i=1; i<formatStringAndPropNames.size(); i++) {
			propsValues[i-1] = (String) props.get(formatStringAndPropNames.get(i));
		}
		result = String.format(formatStringAndPropNames.get(0), propsValues);
		return result;
	}

	public List<String> getTemplateFromExpression(String expression) {
		String formatString = expression;
		Matcher matcher;
		List<String> result = new ArrayList<>();
		while ((matcher = VALUE_PLACE_HOLDER_PATTERN.matcher(formatString)).find()) {
			result.add(matcher.group(1));
			formatString = matcher.replaceFirst("%s");
		}
		result.add(0, formatString);
		return result;
	}
}