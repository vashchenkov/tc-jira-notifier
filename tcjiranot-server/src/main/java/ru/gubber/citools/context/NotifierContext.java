package ru.gubber.citools.context;

/**
 * Contexts' interface.
 * Created by gubber on 24.11.2015.
 */
public interface NotifierContext {
	ContextType getContextType();

	enum ContextType {LABELS}
}