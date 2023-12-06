import SwiftUI
import ComposeApp
import FirebaseCore
import FirebaseAuth

@main
struct iOSApp: App {

    init() {
        HelperKt.doInitKoin()
        FirebaseApp.configure()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
