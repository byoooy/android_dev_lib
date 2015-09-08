package com.android.library.async;

/** 可以控制线程的开启和关闭的控制线程 */
public abstract class ControllThread implements Runnable {

	private boolean suspend = false;

	private Object control = new Object();

	public void setSuspend(boolean suspend) {
		if (!suspend) {
			synchronized (control) {
				control.notifyAll();
			}
		}
		this.suspend = suspend;
	}

	public boolean isSuspend() {
		return this.suspend;
	}

	public void run() {
		while (true) {
			synchronized (control) {
				if (suspend) {
					try {
						control.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			this.runLogic();
		}
	}

	protected abstract void runLogic();
}
