# Unity Chrome Custom Tabs
Unity plugin integration with Chrome Custom Tabs for Android
<br><br>

### INSTALLATION
There are 4 ways to install this plugin:

1. import Unity_ChromeCustomTab.unitypackage via Assets-Import Package
2. clone/download this repository and move the Plugins folder to your Unity project's Assets folder
3. via Package Manager (**Add package from git url**):

    - `https://github.com/onedevapp/Unity_ChromeCustomTabs.git`
4. via Package Manager (add the following line to **Packages/manifest.json**):
    - `"com.onedevapp.chromecustomtab": "https://github.com/onedevapp/Unity_ChromeCustomTabs.git",`

<br>

### Requirements
* You project should build against Android 5.0 (API level 21) SDK at least.
* This plugin uses a custom tool for dependency management called the [Play Services Resolver](https://github.com/googlesamples/unity-jar-resolver)


### How To
Once the Browser Library is added to your project there are two sets of possible customizations:

* Customizing the UI and interaction with the custom tabs.
* Making the page load faster, and keeping the application alive.

The UI Customizations are done by using the CustomTabsIntent and the CustomTabsIntent.Builder classes; the performance improvements are achieved by using the CustomTabsClient to connect to the Custom Tabs service, warm-up the browser and let it know which urls will be opened.


### Opening a Custom Tab
The most basic example to launch a Chrome tab is through a custom intent as shown below:
```C#
ChromeCustomTab.OpenCustomTab(string urlToLaunch, string colorCode, string secColorCode);
```

### Configure the color of the address bar
* One of the most important (and simplest to implement) aspects of Custom Tabs is the ability for you to change the color of the address bar to be consistent with your app's theme.
	```C#
	ChromeCustomTab.OpenCustomTab(string urlToLaunch, string colorCode, string secColorCode);	//"#FF0000" - red
	```

* Toggle title in header toolbar
	```C#
	ChromeCustomTab.OpenCustomTab(string urlToLaunch, string colorCode, string secColorCode, bool showTitle);	
	```

* Hide URL bar on scrolling
	```C#
	ChromeCustomTab.OpenCustomTab(string urlToLaunch, string colorCode, string secColorCode, bool showTitle, bool showUrlBar);	
	```


#### AndroidManifest
Register these activities, receivers and queires in the manifest only when ur choosing Custom Main Manifest in Unity

```XML
	<application>
		<activity
			android:name="com.onedevapp.customchrometabs.WebViewActivity" android:exported="true"/>
		<activity android:name="com.onedevapp.customchrometabs.CustomHeadlessActivity" android:theme="@style/Theme.Transparent" android:exported="true">
		</activity>
		<receiver android:name="com.onedevapp.customchrometabs.ActionBroadcastReceiver" />
	</application>

	<queries>
		<intent>
			<action android:name=
				"android.support.customtabs.action.CustomTabsService" />
		</intent>
	</queries>
```
<br>

### Note
If need to use latest version of Custom Tabs in an app, kindly change the required version at below location:
Plugins/ChromeCustomTab/Editor/ChromeCustomTabDependencies.xml

Available versions can be found [here](https://mvnrepository.com/artifact/androidx.browser/browser).
<br>

## :open_hands: Contributions
Any contributions are welcome!

1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -am 'Add some feature')
4. Push to the branch (git push origin my-new-feature)
5. Create New Pull Request

<br><br>