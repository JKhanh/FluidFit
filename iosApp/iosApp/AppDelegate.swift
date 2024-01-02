//
//  AppDelegate.swift
//  iosApp
//
//  Created by Khanh on 07/12/2023.
//  Copyright © 2023 orgName. All rights reserved.
//
import UIKit
import FirebaseCore
import FirebaseMessaging
import ComposeApp

class AppDelegate: UIResponder, UIApplicationDelegate {
    func application(_ application: UIApplication,
                     didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool
    {
        // [START firebase_configure]
        FirebaseApp.configure()
        NotifierManager.shared.initialize(configuration: NotificationPlatformConfigurationIos.shared)
        return true
    }
}
