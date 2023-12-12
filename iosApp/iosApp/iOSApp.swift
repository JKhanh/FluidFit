import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    init() {
        HelperKt.doInitKoiniOS()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
