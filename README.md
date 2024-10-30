# CxHub-Android-Demo-App
В этом репозитории находится проект, предназначенный для демонстрации интеграции CxHub SDK Android в приложение. 
Так же нужно добавить свой файл app/src/google-services.json, или agconnect-services.json, или добавить в код идентификатор для rustore.
Так же необходимо добавить файл app/src/main/res/values/cxhub.xml следующего вида:
```xml
<resources>
    <string name="cxhub_resource_icon_id">drawable/ic_message</string>
    <string name="cxhub_integration_id" translatable="false">[ваш id интеграции из личного кабинета]</string>
    <string name="cxhub_application_secret" translatable="false">[ваш секретный ключ интеграции из личного кабинета]</string>
    <string name="cxhub_api_host" translatable="false">[url вашего проекта]/callback-service/</string>
    <bool name="cxhub_trust_all_certificates">[true для stage-окружения, если в данном вам адресе проекта нет "stage" - строка не нужна]</bool>
</resources>
```
А также раскомментировать необходимые зависимости в app/build.gradle.kts, в зависимости от того какие пуши вы используете в реальном приложении. 
Полное описание доступно в [документации] (https://github.com/cxhub-corp/CxHub-Android-Demo-App-New//blob/master/programmers_guide.pdf).