# BulkDownloader
Original: https://github.com/BalakrishnanPT/BulkDownloader

## Edits:
Edited so downloaded images will be saved into a directory {package_name}/images

### You can use this Downloader, where:
You want to download 100's of images in background
You want progress of each file being downloaded and total images downloaded.

### What this can do:
- You can download bunch of images in a go
 -You can assign n number of downloading tasks and this library can handle this pretty well
- You can assign 10 downloading jobs that has 100s of images each, each downloading batch gives you progress and as well as each image progress in percentage
- Let's say you have to download unknown number of images from a response,you don't have to parse the response to get image urls you can just pass the response and this will handle everything for you.
- You can specify when to download the images lets say you have to download only when internet is at good speed or download over mobile data or not to download when app is in doze mode.
- You can get the information of downloading in receiver if app is not running. You can write logic in receiver for local notification
### In development
- As of now this library can support only images but it is developed in such way that it could support uploads and all formats.
- To specify what to do when each file is downloaded. Let say you have to convert a response into video or image and resizing it.
### Implementation
Copy and paste this dependency in module gradle

`implementation 'balakrishnan.me.bulkdownloader:bulkdownloader:0.0.5'`

>Implement `ImageDownloaderHelper.DownloadStatus` in the activity or fragment that you want to track your record or you can
follow below given method in the Activity or Fragment


If you wanted to download a list of images then call BulkDownloader as follows
```java
        new ImageDownloaderHelper().setUrls(ArrayList of urls))
                    .setDownloadStatus(getCallback())
                    .setCollectionId(uniqueId)
                    .create();
```
getCallback() method is as follows
```java
   public ImageDownloaderHelper.DownloadStatus getCallback() {
        return new ImageDownloaderHelper.DownloadStatus() {
            @Override
            public void DownloadedItems(int totalurls, int downloadPercentage, int successPercent, int failurePercent) {
               //You can get Total Image Downloaded progress here
            }

            @Override
            public void CurrentDownloadPercentage(LinkedHashMap<String, ProgressModel> trackRecord) {
              //You can get Induvidual Image Downloaded progress here
            }

        };
    }
```

Let's say if you want to download all images in a any response

Example:
```json
[
{"id":"1","createdAt":"2018-10-22T23:34:56.999Z","name":"Cornelius Fadel","avatar":"https://s3.amazonaws.com/uifaces/faces/twitter/areus/128.jpg"},
{"id":"2","createdAt":"2018-10-22T22:59:42.972Z","name":"Freeman Balistreri","avatar":"https://s3.amazonaws.com/uifaces/faces/twitter/matbeedotcom/128.jpg"},
{"id":"3","createdAt":"2018-10-22T19:08:53.211Z","name":"Angus Johnson","avatar":"https://s3.amazonaws.com/uifaces/faces/twitter/cdharrison/128.jpg"},
{"id":"4","createdAt":"2018-10-23T01:38:08.226Z","name":"Ashleigh Baumbach","avatar":"https://s3.amazonaws.com/uifaces/faces/twitter/michaelmartinho/128.jpg"},
{"id":"5","createdAt":"2018-10-23T14:08:41.273Z","name":"Sasha Bernhard","avatar":"https://s3.amazonaws.com/uifaces/faces/twitter/kikillo/128.jpg"},
.
.
.
{"id":"24","createdAt":"2018-10-23T15:12:14.040Z","name":"Mr. Barton Hickle","avatar":"https://s3.amazonaws.com/uifaces/faces/twitter/markolschesky/128.jpg"}
]
```

You need to add following code in your activity / Fragment
```]ava
        new ImageDownloaderHelper().setDownloadStatus(getCallback())
                    .setUrl(`some_url`)
                    .setCollectionId(uniqueId)
                    .createImageDownloadWorkURl();
```
getCallback() method is as follows
```java
   public ImageDownloaderHelper.DownloadStatus getCallback() {
        return new ImageDownloaderHelper.DownloadStatus() {
            @Override
            public void DownloadedItems(int totalurls, int downloadPercentage, int successPercent, int failurePercent) {
               //You can get Total Image Downloaded progress here
            }

            @Override
            public void CurrentDownloadPercentage(LinkedHashMap<String, ProgressModel> trackRecord) {
              //You can get Induvidual Image Downloaded progress here
            }

        };
    }
```

Receiving download status if app is not running

1. Create a receiver for receiving info even if app is not running
[Receiver](https://github.com/BK24/BulkDownloader/blob/master/app/src/main/java/balakrishnan/me/downloader/Receiver.java)
2. Register the receiver in [AndroidManifest.xml](https://github.com/BK24/BulkDownloader/blob/master/app/src/main/AndroidManifest.xml) and [Application Class](https://github.com/BK24/BulkDownloader/blob/master/app/src/main/java/balakrishnan/me/downloader/SubApplication.java)

You will get information in the receiver of download only if app is not running
