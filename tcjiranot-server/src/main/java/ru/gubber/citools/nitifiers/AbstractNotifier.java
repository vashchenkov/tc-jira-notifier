package ru.gubber.citools.nitifiers;

import ru.gubber.citools.context.NotifierContext;

/**
 * Abstract notifier. Defines the constructor that holds the context.
 * Created by gubber on 24.11.2015.
 */
public abstract class AbstractNotifier<R extends NotifierContext> implements JiraNotifier {

	protected R context;

	AbstractNotifier(R context) {
		this.context = context;
	}
}