# BiometricStationFX project - Jelle De Buyzere

Had to use a Platform.runLater() for updating GUI components. Otherwise it would be possible to get a concurrentModificationException on a random moment.

The heartbeat gui will give the right values after it had -10 as a value for a couple of seconds.

The full application will terminate the broker + javaFx when pressing on close button.
