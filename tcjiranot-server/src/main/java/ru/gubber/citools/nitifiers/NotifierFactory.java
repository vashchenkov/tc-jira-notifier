package ru.gubber.citools.nitifiers;

import ru.gubber.citools.context.JiraLabelSetterContext;
import ru.gubber.citools.context.NotifierContext;

/**
 * Generate concrete notifier based on type of context.
 * Created by gubber on 24.11.2015.
 */
public class NotifierFactory {

	public static JiraNotifier generateNotifierForContext(NotifierContext context) {
		switch (context.getContextType()) {
			case LABELS:
				return new LabelsJiraNotifier((JiraLabelSetterContext) context);
			default:
				throw new IllegalArgumentException("Unknown context. Cant generate notifier");
		}
	}
}