using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace OneDevApp.CustomTabPlugin
{
    public class ChromeCustomTab : MonoBehaviour
    {

#pragma warning disable 0414
        /// <summary>
        /// UnityMainActivity current activity name or main activity name
        /// Modify only if this UnityPlayer.java class is extends or used any other default class
        /// </summary>
        [Tooltip("Android Launcher Activity")]
        [SerializeField]
        private string m_unityMainActivity = "com.unity3d.player.UnityPlayer";

#pragma warning restore 0414

        public void OpenCustomTab(string urlToLaunch, string colorCode, string secColorCode, bool showTitle = false, bool showUrlBar = false)
        {
            if (Application.platform == RuntimePlatform.Android)
            {
                using (var javaUnityPlayer = new AndroidJavaClass(m_unityMainActivity))
                {
                    using (var mContext = javaUnityPlayer.GetStatic<AndroidJavaObject>("currentActivity"))
                    {
                        using (AndroidJavaClass jc = new AndroidJavaClass("com.onedevapp.customchrometabs.CustomTabPlugin"))
                        {
                            var mAuthManager = jc.CallStatic<AndroidJavaObject>("getInstance");
                            mAuthManager.Call<AndroidJavaObject>("setActivity", mContext);
                            mAuthManager.Call<AndroidJavaObject>("setUrl", urlToLaunch);
                            mAuthManager.Call<AndroidJavaObject>("setColorString", colorCode);
                            mAuthManager.Call<AndroidJavaObject>("setSecondaryColorString", secColorCode);
                            mAuthManager.Call<AndroidJavaObject>("ToggleShowTitle", showTitle);
                            mAuthManager.Call<AndroidJavaObject>("ToggleUrlBarHiding", showUrlBar);
                            mAuthManager.Call("openCustomTab");
                        }
                    }
                }
            }
        }
    }
}
