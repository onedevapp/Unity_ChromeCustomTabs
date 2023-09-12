using UnityEngine;
using UnityEngine.UI;

namespace OneDevApp.CustomTabPlugin.Demo
{
    public class ChromeCustomTabDemo : MonoBehaviour
    {
        public InputField urlInputField;
        public Button openUrlBtn;

#if UNITY_ANDROID && !UNITY_EDITOR
        ChromeCustomTab chromeCustomTab;
#endif

        // Start is called before the first frame update
        void Start()
        {
#if UNITY_ANDROID && !UNITY_EDITOR
            chromeCustomTab = GetComponent<ChromeCustomTab>();
#endif

            urlInputField.text = "http://www.google.com";
            openUrlBtn.onClick.AddListener(()=> {

                if (string.IsNullOrEmpty(urlInputField.text)) return;

#if UNITY_ANDROID && !UNITY_EDITOR
                chromeCustomTab.OpenCustomTab(urlInputField.text, "#000000", "#000000");
#endif
            });
        }

    }

}