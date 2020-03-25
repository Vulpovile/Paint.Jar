package ca.limeware.masteroffice.paintmaster.plugin.event;

public abstract class Event {
	protected Type evtType;
	public Event()
	{
		evtType = Type.NONE;
	}
	/**
	 * Specify the type of event
	 */
	public static enum Type {
	    NONE, MOUSE_MOVE, MOUSE_CLICK, MOUSE_DRAG, MOUSE_PRESS, MOUSE_RELEASE, MOUSE_ENTER, MOUSE_EXIT, PLAYER_DISCONNECT, PLAYER_JOIN
	}
	/**
	 * The order in which an EventListener will be called. <br>
	 * {@linkplain Priority.REALTIME} reserved for mission critical plug-ins.
	 */
	public static enum Priority {
		/**
		 * Avoid using. This is the absolute highest priority and is reserved for mission critical plug-ins such as permission managers.
		 */
		REALTIME, 
		/**
		 * Highest normal priority. Used when the plug-in should receive the event earlier than most plug-ins.
		 */
		HIGHEST, 
		/**
		 * Higher than normal priority. Used when the plug-in should receive the event earlier than most plug-ins, but is not as critical.
		 */
		HIGH, 
		/**
		 * Normal priority. Used when the plug-in should receive the event at a normal time, and it does not matter that any plug-in processed it earlier or will process it after.
		 */ 
		NORMAL, 
		/**
		 * Low priority. Used when the plug-in should receive the event after most plug-ins have already processed the event.
		 */
		LOW, 
		/**
		 * Lowest priority. Used when the plug-in should receive the event after most plug-ins have already processed the event and it is crucial to be the last to process it.
		 */
		LOWEST
	}
	private boolean cancelled = false;
	/**
	 * Sets the event as cancelled. The event will not continue on the application and will be reverted, unless a subsequent plug-in calls {@linkplain unCancel()} on the event.
	 */
	public void cancel() {
		this.cancelled = true;
	}
	/**
	 * Un-Cancel's the event if a previous listener called {@link cancel()}. The event will continue as usual, unless a subsequent plug-in calls {@linkplain cancel()} on the event.
	 */
	public void unCancel() {
		this.cancelled = false;
	}
	
	/**
	 * Returns {@link cancel()} was called on the event.
	 */
	public boolean isCancelled() {
		return this.cancelled;
	}
	public Type getType() {
		// TODO Auto-generated method stub
		return evtType;
	}

}
