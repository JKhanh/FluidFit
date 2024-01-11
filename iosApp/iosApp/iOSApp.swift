import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    init() {
        HelperKt.doInitKoiniOS()
        NotifierManager.shared.initialize(configuration: NotificationPlatformConfigurationIos.shared)
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
