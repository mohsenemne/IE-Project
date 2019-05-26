-- MySQL dump 10.13  Distrib 8.0.16, for osx10.14 (x86_64)
--
-- Host: localhost    Database: joboonja
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Bid`
--

DROP TABLE IF EXISTS `Bid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Bid` (
  `userId` char(150) NOT NULL,
  `projectId` char(150) NOT NULL,
  `bidAmount` int(11) DEFAULT NULL,
  PRIMARY KEY (`userId`,`projectId`),
  KEY `projectId` (`projectId`),
  CONSTRAINT `bid_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`username`),
  CONSTRAINT `bid_ibfk_2` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Bid`
--

LOCK TABLES `Bid` WRITE;
/*!40000 ALTER TABLE `Bid` DISABLE KEYS */;
/*!40000 ALTER TABLE `Bid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Endorsement`
--

DROP TABLE IF EXISTS `Endorsement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Endorsement` (
  `endorserId` char(150) NOT NULL,
  `endorsedId` char(150) NOT NULL,
  `skillName` char(150) NOT NULL,
  PRIMARY KEY (`endorserId`,`endorsedId`,`skillName`),
  KEY `endorsedId` (`endorsedId`),
  KEY `skillName` (`skillName`),
  CONSTRAINT `endorsement_ibfk_1` FOREIGN KEY (`endorserId`) REFERENCES `user` (`username`),
  CONSTRAINT `endorsement_ibfk_2` FOREIGN KEY (`endorsedId`) REFERENCES `user` (`username`),
  CONSTRAINT `endorsement_ibfk_3` FOREIGN KEY (`skillName`) REFERENCES `skill` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Endorsement`
--

LOCK TABLES `Endorsement` WRITE;
/*!40000 ALTER TABLE `Endorsement` DISABLE KEYS */;
/*!40000 ALTER TABLE `Endorsement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Project`
--

DROP TABLE IF EXISTS `Project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Project` (
  `id` char(150) NOT NULL,
  `title` char(150) DEFAULT NULL,
  `description` char(150) DEFAULT NULL,
  `imageURL` char(150) DEFAULT NULL,
  `budget` int(11) DEFAULT NULL,
  `deadline` mediumtext,
  `creationDate` mediumtext,
  `winner` char(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Project`
--

LOCK TABLES `Project` WRITE;
/*!40000 ALTER TABLE `Project` DISABLE KEYS */;
INSERT INTO `Project` VALUES ('02b73af0-69f0-4ea1-ba52-18762513b564','تغییر cms قدیمی به ورد پرس','نیاز مند تغییر cms  یکی از وب سایت های ساخته شده توسط شرکت خودمان هستیم. \n\nاین وب سایت دارای cms بر مبنای php است که نیاز داریم به wp تبدیل ش...','https://cdn.dribbble.com/users/926537/screenshots/4502924/python-2.gif',1500000,'1558781826000','1558780501550',NULL),('0e240cfc-0638-441d-89bf-e11256acf3d0','افزایش سرعت سایت و بهینه سازی دیتابیس','میخوام که سرعت سایت خصوصا برای کاربران داخل ایران به طور محسوسی بالاتر بره. همینطور دیتابیس سایت هم بهیه سازی بشه.لازم به ذکر که سایت ما، سایت نسبتا پ','https://ponisha.ir/defaults/category-character/1.png',500000,'1558874666000','1558873202599',NULL),('12ff517b-fe42-4aff-928e-51020c2c66bf','پکیج برنامه های ساده جاوا','با سلام ، برنامه های زیر بایستی با زبان جاوا تماما شی گرا بوده و با استفاده از کلاس ها پیاده سازی شود. ***برنامه اول چند جمله ای\n \nکلاس...','https://ponisha.ir/defaults/category-character/3.png',150000,'1558787437000','1558786802098',NULL),('15e23ee7-44be-487a-94da-6a1b8169e5f5','دانلود ویدئو از یوتیوب','سلام من یه اسکریپت میخام که آی دی یوتیوب از من بگیره و فیلم Mp4 کیفیت 360p ویدئو رو روی هاست خودش دانلود کنه همچنین کنار فایل ویدئویی ،...','https://ponisha.ir/defaults/category-character/2.png',500000,'1558874890000','1558873502302',NULL),('193e22ba-4318-4865-af93-8bd54818d73b','آموزش و همکاری در طراحی مدار جمع کننده مربوط به مقاله','با سلامپروژه مربوط به طراحی مدار جمع کننده موجود در مقاله پیوست می باشد. مجری باید مقاله را بتواند خوب درک کند و مدار مربوطه را طراحی کند.سپس در محیط.','https://recruitingdaily.com/wp-content/uploads/sites/6/2018/04/28-year-old-software-engineer.jpg',500000,'1558877599000','1558876502141',NULL),('1a42dad7-e615-41f2-95ea-f20bc32de639','تبدیل کدهای برنامه از vb به nod و خروجی گرفتن برای لینوکس','من یک اپلیکیشن دارم که تو vb طراجی شده الان می خوام از این برنامه یه خروجی بگیرم که تو لینوکس اجرا بشهمی خوام کدها  به nod.js تبدیل بشه که بتونیم خ...','https://ponisha.ir/asset/v1/img/how-it-work/character-contest.png',250000,'1558870669000','1558869902128',NULL),('1b995d2b-d2a5-403f-8254-11463fdcb1c4','نوشتن یک تابع اضافی برای پلاگین ووکامرس','سلامقالب سایت من قالب kupon ووکامرس هست؛ برای فروش تخفیف های گروهی پلاگین WooCommerce PDF Vouchers رو خریداری، نصب و کانفیگ کردم مشتری...','https://cdn.dribbble.com/users/926537/screenshots/4502924/python-2.gif',250000,'1558872228000','1558870501894',NULL),('1c771581-ff31-4cb2-b6af-3844bde2c759','وب سایت برون سپاری پروژه','یک سایت میخوام با مشخصات ذیل  1-  بازاریابی 2-  ثبت پروژه جدید 3-  فروش فایل ( به صورت اشتراک یا قیمت مشخص) 4-  درخواست سفارشی کردن فایل 5- ...','https://ponisha.ir/defaults/category-character/2.png',1500000,'1558869142000','1558868101994',NULL),('21ab7c2d-6bde-4899-b9ad-dfeffd3c9491','نرم افزار کانورتور pdf , mp4 به exe','بنده یک کانورتور تبدیل فایل های pdf  و mp4  به exe  میخواستم .میخوام فایل های مقاله و فیلم را تبدیل و بفروشم و فقط روی یک سیستم اجرا شود یعنی کد سخت ا','https://cdn4.vectorstock.com/i/1000x1000/31/48/software-developer-and-programmer-vector-10673148.jpg',350000,'1558783568000','1558782902320',NULL),('259a09f5-6f11-40d7-b7d4-39aee5d5e2e0','تغییرات در قالب یک سایت','سلام خسته نباشید یک وب سایت داریم.این سایت نمایندگی هست و امکان دسترسی به فایل ها و کدهارو نداریم از طریق پنل می تونیم موارد زیر...','https://cdn4.vectorstock.com/i/1000x1000/49/03/software-developer-vector-12104903.jpg',500000,'1558872223000','1558871101494',NULL),('311b4ac7-34a1-4b72-baa3-dd9ba6654d42','باز طراحی قالب سایت','ما یک وبسایت داریم و میخوایم قالبش باز طراحی بشه فرد مورد نظر باید خودش بتونه براساس استانداردهای لازم پیشنهاد بده و باید کاربر پسند باشه در مجموع...','https://recruitingdaily.com/wp-content/uploads/sites/6/2018/04/28-year-old-software-engineer.jpg',200000,'1558788185000','1558787402726',NULL),('3142c933-2fcf-4e74-92f6-fe9ff02e905f','ریپ کردن قالب یک سایت خارجی و راست چین کردن قالب','یکی از مشتریان مجموعه ما سایت خارجی ای رو دیده که عینا همون قالب سایت رو میخواد، که نزدیک به 12 صفحه (شامل صفحه اصلی، صفحات پروفایل و محصول و ...) میش','https://pureelementwater.com/wp-content/uploads/2017/07/proba.png',500000,'1558779367000','1558778702119',NULL),('326a7390-685f-4059-abdb-7f397d36c2c3','قفل برنامه ها و گالری مخفی + امکانات خاص !','بسمه تعالی قفل برنامه ها و گالری مخفی +  امکانات خاص ! از برنامه نویسان حرفه ای دعوت میشود که در ساخت این پروژه ما را یاری کنند ب...','https://ponisha.ir/defaults/category-character/3.png',6000000,'1558869403000','1558868401967',NULL),('3435421f-a136-40d4-b14e-e9e4e1fbce03','انبار داری بیمارستان با جاوا','با سلامیه برنامه خیلی ساده انبار داری میخوام که بدون طرح گرافیکی باشه و توی cmd اجرا بشه و همون اول که برنامه اجرا شد 3 عدد نمایش بده  ...','https://ponisha.ir/defaults/category-character/3.png',250000,'1558786289000','1558785001877',NULL),('392e09f3-e3ec-4051-9634-6181fb444ce9','افزایش سرعت سایت و بهینه سازی دیتابیس','میخوام که سرعت سایت خصوصا برای کاربران داخل ایران به طور محسوسی بالاتر بره. همینطور دیتابیس سایت هم بهیه سازی بشه.لازم به ذکر که سایت ما، سایت نسبتا پ','https://recruitingdaily.com/wp-content/uploads/sites/6/2018/04/28-year-old-software-engineer.jpg',500000,'1558869679000','1558869002095',NULL),('399e93bc-67c6-4dff-b232-1dd8da9a3dcb','توسعه حلگر نانوسیال مغناطیسی','نرم افزار اپن فوم (openFoam) از جمله نرم افزارها در تحلیل جریان سیال می‌باشد و در مهندسی مکانیک شاخه تبدیل انرژی کاربرد زیادی دارد. این نرم افزار دارا','https://cdn4.vectorstock.com/i/1000x1000/31/48/software-developer-and-programmer-vector-10673148.jpg',6000000,'1558873086000','1558871702267',NULL),('39d15a36-a5e7-47a2-9abf-9624efd37e4f','Busstation','یک اپ ساده موبایل که با استفاده از API Rest اطلاعات مربوط به زمان ورود قطارها را نشان دهد.زمان انجام پروژه 2 هفته است نمونه انجام شده آن :','https://cdn.dribbble.com/users/926537/screenshots/4502924/python-2.gif',250000,'1558780644000','1558779601905',NULL),('3a35f6a1-3f5e-4318-beee-a9c65e680cf2','وب اسکرپینگ و برنامه نویسی با پایتون','با سلام\n \nوقت بخیر\n پروزه در دو فاز تعریف شده هست لطفا نظراتو نوانایی انجام هر فاز را به صورت جدابفرمایید فاز اول: یک پروزه...','https://ponisha.ir/asset/v1/img/how-it-work/character-project.png',500000,'1558868295000','1558867501980',NULL),('5a18806d-7341-461b-a96d-33b8c8c776e7','پروژه ساخت اپلیکیشن امــــلا*کـــــ','دوستان عزیز ابتدا کمی تعمل کنید و پروژه رو بررسی کنید بعد پیشنهاد بزنید وب سایت کار موجود و api سایت در دسترس برای طراحی پروژه سایت امــلا*کـ...','https://cdn1.vectorstock.com/i/1000x1000/20/90/network-engineer-administrator-working-in-data-vector-17652090.jpg',4000000,'1558787909000','1558787101814',NULL),('5d7696ec-a1bc-4584-bceb-708fdd8137c8','تبدیل pdf به word','چندتا فایل pdf دارم میخوام با سرعت بسیار بالا تبدیل به word بشههر روز هم چنتا فایل جدید به پروژه اضافه میشه فعلا حدود 13 تا فایل هستدوستانی که میتونن.','https://ponisha.ir/defaults/category-character/1.png',150000,'1558785883000','1558784101662',NULL),('60a7dee4-2565-49c9-b8aa-d058c428f5a5','کنترل عبور و مرور','سلامپروژه ی من یه پروژه ی حضور و غیاب خیلی سادسو قراره داخل سیستم عامل لینوکس پیاده سازی بشهقراره که داخل یه پایگاه داده یه سری کد 8 رقمی و به همراه ی','https://ponisha.ir/defaults/category-character/3.png',250000,'1558783583000','1558782302212',NULL),('615545b3-7666-4416-a3ce-7f062e0626b0','ارسال خودکار مطالب یک سایت به شبکه های اجتماعی','سینا صبوری:\nبنام خدا \n\nجزییات پروژه به شرح زیر می باشد:  \n\n1- ارسال مطالب بایستی برای شبکه های اجتماعی زیر انجام شود:\n* تلگرام\n* فیس بو...','https://cdn4.vectorstock.com/i/1000x1000/49/03/software-developer-vector-12104903.jpg',250000,'1558873515000','1558872302093',NULL),('728436cf-4d5c-408a-8b6f-5e950dfc4ef1','نصب ftp بر روی CentOs7','نیاز است به نصب ftp از روش بر روی سرور موجود که سیستم عامل آن CentOs7 است * هر کاری که انجام می دهید باید بصورت مکتوب در یک فایل word گزارش ب...','https://cdn1.vectorstock.com/i/1000x1000/20/90/network-engineer-administrator-working-in-data-vector-17652090.jpg',105000,'1558780789000','1558779901609',NULL),('73555575-99a1-4f67-8504-b1fa09914b01','تولید گزارشات مختلف از روی یک دیتابیس','سلام و خسته نباشیدما یک دیتابیس POSTGRESQL داریم که دارای جدوال مختلفی هستش نیار به یک برنامه نویس که دارای مهارت کافی در زمینه SQL  باشه و م...','https://ponisha.ir/defaults/category-character/1.png',200000,'1558876750000','1558875002731',NULL),('74535fff-dec2-40e5-b409-9efe183b78c1','طراحی و اجرای پایگاه داده و API های اپلیکیشن \"تخفیف گروهی\"','این پروژه طراحی و پیاده سازی سمت سرور (Back-end) یک اپلیکیشن تحت وب \"تخفیف گروهی\" است. مستندات (ِDocuments)در این پروژه بسیار مهم هستند.  ...','https://cdn1.vectorstock.com/i/1000x1000/20/90/network-engineer-administrator-working-in-data-vector-17652090.jpg',4000000,'1558877152000','1558875602546',NULL),('74e4f840-72dd-469e-8e9b-bd35b87b4738','دیکشنری ساده با ترمینال java','سلام یک پروزه  برای پیاده سازی یک دیکشنری تحت جاوا است که یک فایل 100,000 تایی با فرمت .db داده می شودکه باید با الگوریتم trie  پیاده شود و بتواند...','https://cdn1.vectorstock.com/i/1000x1000/71/55/software-development-vector-5647155.jpg',250000,'1558779637000','1558779002207',NULL),('75beb32b-dae9-4ba7-ae2c-709912a17699','اسکریپت نویسی و کار با نقشه : بک و فرانت','سلام، ابتدا نیز به یک اسکریپ نویسی دارم که اطلاعات را بصورت اتوماتیک جمع آوری کند. لطفا به سایت زیر نگاه کنید.  https://www.schoolfinder.com/...','https://ponisha.ir/asset/v1/img/how-it-work/character-contest.png',6000000,'1558783354000','1558782002472',NULL),('788ed75a-6229-4015-a3ec-9cbe4974c698','وب اسکرپینگ','سایت موزیک می باشد که باید با وب اسکرپینگ اطلاعات و لینک های نهایی اون در قالب دیتابیس تحویل داده شود اطلاعاتی از قبیل نام اهنگ نا خواننده لینک کاور و','https://pureelementwater.com/wp-content/uploads/2017/07/proba.png',250000,'1558878982000','1558878001913',NULL),('7ac9d1be-18c1-4d63-a7e6-31e5b119f234','ساخت ایمیل برای cpanel','سلاممیخام در سی پنل تعداد زیادی ایمیل بسازم.به این شکل که وروردی هارو بدیم ( دامنه مورد نظر ، اسم ایمیل ، پسورد ، فضای مورد نیاز) برامون ایمیل هارو بس','https://www.inetsolutions.org/wp-content/uploads/2018/11/How-to-Select-the-Correct-Web-Design-Company-for-Your-Website.png',50000,'1558877759000','1558877102032',NULL),('7f96c618-b4bc-4954-b066-c1a0d5a35f75','طراحی لوگو برای وب سایت فروشگاه اینترنتی','یک لوگوی فارسی و انگلیسی برای فروشگاه اینترنتی tienda تیاندا میخواهمtienda کلمه اسپانیایی است به معنای مغازه Shop','https://cdn.dribbble.com/users/926537/screenshots/4502924/python-2.gif',500000,'1558873810000','1558872901626',NULL),('805d80d5-caff-4aa2-a520-605610d668ce','ورود و طراحي وردپرس','كانفيگ كلي قالب ورد پرس (انفلد) و افزونه ها  توليد و ورود محتوا  توليد محتوا و سئو  راه اندازي فرو شگاه قالب و اپ و اضافه كردن مو...','https://cdn.dribbble.com/users/926537/screenshots/4502924/python-2.gif',500000,'1558783187000','1558781702136',NULL),('807133a8-b32b-4564-a24f-92110bfef3a6','کنترل عبور و مرور','سلامپروژه ی من یه پروژه ی حضور و غیاب خیلی سادسو قراره داخل سیستم عامل لینوکس پیاده سازی بشهقراره که داخل یه پایگاه داده یه سری کد 8 رقمی و به همراه ی','https://ponisha.ir/defaults/category-character/2.png',250000,'1558879400000','1558878301677',NULL),('82429dc1-58bf-4344-97a6-49b71b9c1a6c','آموزش ساخت بازی منچ آنلاین با یونیتی و Node.js','سلام دوستان من نیازمند ساخت یک آموزش اختصاصی هستم ، ساخت یک منچ آنلاین با Node.js و socketio که کاملا دقیق و ریز آموزش داده بشه ، میخوام خودم یاد بگیر','https://ponisha.ir/asset/v1/img/how-it-work/character-project.png',500000,'1558869245000','1558867801902',NULL),('86be9e94-e227-4a87-867c-d4cedb551e5c','طراحي و پياده سازي سيستم الكترونيكي بانكي','پياده سازي سيستم الكترونيكي بانكي با زبان سي شارپ ، و داشتن بانك اطلاعاتي( sql) از كاربران (براي پروژه دانشگاهي) كه در visual studio اجرا شود در فضايي','https://ponisha.ir/asset/v1/img/how-it-work/character-project.png',250000,'1558789414000','1558787702417',NULL),('8d173722-24ef-4c76-bf78-72c35c5dd477','نرم افزار تبادل *کوین بازی ویدئویی* به صورت خودکار','سلام و خسته نباشید ، همچنین سایت طراحی شده باید واکنش گرا باشد. و از نظر تعریف \"پنل کاربری\" میبایست با سایت خودمون تطبیق داده شود!','https://cdn.dribbble.com/users/926537/screenshots/4502924/python-2.gif',4000000,'1558872196000','1558871402478',NULL),('8ef8d89d-3ce0-4fd0-b37e-ac1cca5a4837','ساخت و توسعه بازی تلگرامی','با سلامتیم ما به دنبال راه اندازی یک بازی تلگرامی سوال و پاسخ با سبکهای اطلاعات عمومی، ورزشی، سینمایی، سرگرمی، علمی، تاریخی، ریاضی و جغرافیایی می ب...','https://cdn4.vectorstock.com/i/1000x1000/49/03/software-developer-vector-12104903.jpg',750000,'1558870328000','1558869602732',NULL),('903313ce-4b45-4483-bcd9-dc7f41d3d3a6','ربات تلگرام ارایه آرشیو لینک و جستجو متصل به وردپرس','پروژه یا با Pyton باشه یا PHP. تمیز از یک دیتابیس MySQL سایت وردپرس Search هوشمند کنه و لینک مستقیم و توضيحات نمایش بده. پارامتر های ورودی قر...','https://cdn1.vectorstock.com/i/1000x1000/20/90/network-engineer-administrator-working-in-data-vector-17652090.jpg',1500000,'1558785866000','1558784402706',NULL),('9721e658-b883-4119-87ee-28989d0e6048','روبات یا اسکریپت استعلام موجودی یک محصول در یک سایت','ما یک روبات یااسکریپت میخواهیم که بتواند عملیات زیرراانجام دهد  روبات بصورت دستی یا اتوماتیک وارد سایت بشود و در بخش استعلام سایت  بررس...','https://ponisha.ir/defaults/category-character/4.png',500000,'1558871073000','1558869301801',NULL),('98469025-5b1b-4aad-837b-08033e908dd3','باشگاه مشتریان','یک باشگاه مشتریان برای فروشگاه های زنجیره ای حجاب فاطمی نیاز دارم که در بستر اپ اندروید و البته در مرحله بعد اپ آیفون طراحی شود که امکانات و خواسته ها','https://pureelementwater.com/wp-content/uploads/2017/07/proba.png',2500000,'1558878489000','1558877402104',NULL),('984a85a7-d95f-4763-9784-055fdda595d1','آموزش و همکاری در طراحی مدار جمع کننده مربوط به مقاله','با سلامپروژه مربوط به طراحی مدار جمع کننده موجود در مقاله پیوست می باشد. مجری باید مقاله را بتواند خوب درک کند و مدار مربوطه را طراحی کند.سپس در محیط.','https://ponisha.ir/defaults/category-character/3.png',500000,'1558782414000','1558780802612',NULL),('9bd919fa-b78f-422f-82f9-861092f5eb19','نصب نود.جی اس برروی لینوکس و وب سرور انجینیکس','نصب node.js برروی وب سرور انجینیکس و لینوکس.فقط میخوام نصب شه و با موفقیت ران شه و ویو داشته باشم رو مرورگر.سرورشم دارم','https://cdn1.vectorstock.com/i/1000x1000/20/90/network-engineer-administrator-working-in-data-vector-17652090.jpg',100000,'1558877913000','1558876802056',NULL),('9d3ee0a2-234e-4c17-bff5-594512ab7569','برنامه بهینه سازی با پایتون','طراحی یک الگوریتم با پایتون برای بدست اوردن جواب های بهینه یک مناقصه در اين مناقصه تعدادي فروشنده و تعدادي خريدار هستند خريدار ها به صورت در بسته پ...','https://cdn1.vectorstock.com/i/1000x1000/71/55/software-development-vector-5647155.jpg',200000,'1558784567000','1558783501990',NULL),('9e57696a-e70e-417e-9bb0-aa78127ed684','تبدیل تعدادی قالب psd به اندروید','سلام ، تعدادی قالب psd دارم که میخوام تبدیل بشه به اندروید، این کار باید تا دوشنبه هفته بعد تموم شه.\n قالب ها تعدادشون 21 تا هستش و میخوام که...','https://cdn1.vectorstock.com/i/1000x1000/71/55/software-development-vector-5647155.jpg',500000,'1558876301000','1558874701863',NULL),('9fd02c1b-93d2-4dbe-9431-a3221253a75d','پارسر پیچیده XML و Txt توسط PYTHON و یا NODE.JS','سلام.پارسر XML و TEXT پیچیده و حجیم  ترجیحا توسط PYTHON و با استفاده از REGEX و ریختن آن در دیتابیس. نمونه فایل ورودی XML , فایل پازس شده در...','https://pureelementwater.com/wp-content/uploads/2017/07/proba.png',700000,'1558784860000','1558783202251',NULL),('a632047f-3431-4604-8b7a-37c97d4c33bb','ایجاد ربات تلگرام هواشناسی','با سلام \n\nربات مد نظر باید دارای امکانات زیر باشد: \n\n1- اعلام اطلاعات دقیق آب و هوای روز جاری \n\n2- اعلام پیش بینی آب و هوای روزها...','https://ponisha.ir/defaults/category-character/3.png',750000,'1558786870000','1558785602727',NULL),('af8a9c7b-14ab-4500-8fef-db9baca0f56f','دسترسی به تمام امکانات کار با کانال از طریق CLI','هدف در این پروژه ایجاد تابع/برنامه‌ای است که بتواند تعداد اعضای عضو یک کانال تلگرام، پست های کانال و تعداد ویو های هر پست و آی دی پست را گزارش کند....','https://www.inetsolutions.org/wp-content/uploads/2018/11/How-to-Select-the-Correct-Web-Design-Company-for-Your-Website.png',750000,'1558875855000','1558874101631',NULL),('b8ddfa5e-a929-4590-8ff2-f507ccafa460','برنامه اندروید جهت اتصال به Cisco anyconnect','این پروژه از لحاظ ظاهری همانند برنامه Turbo VPN  می باشد و در عمل برای اتصال به یک سرور اوبونتو 18 که سیسکو انی کانکت بر روی آن نصب و پیکره بندی گر...','https://pureelementwater.com/wp-content/uploads/2017/07/proba.png',3000000,'1558875208000','1558874402558',NULL),('bc1336a6-4951-433a-b7ac-5cfa91e7049d','پایتون','انجام پروژه پیاده سازی اسکریپت و دیتابیس ارسالی در محیط پایتون و ارائه خروجی آن و مقایسه آن با نتایج مقاله و ارائه گزارش کار کلی به همراه توضیحات پیاد','https://pureelementwater.com/wp-content/uploads/2017/07/proba.png',500000,'1558871456000','1558870801859',NULL),('c49a5afd-c7ba-4bf3-97d4-6dcf68eaca96','افزایش سرعت سایت و بهینه سازی دیتابیس','میخوام که سرعت سایت خصوصا برای کاربران داخل ایران به طور محسوسی بالاتر بره. همینطور دیتابیس سایت هم بهیه سازی بشه.لازم به ذکر که سایت ما، سایت نسبتا پ','https://ponisha.ir/defaults/category-character/1.png',500000,'1558870033000','1558868702096',NULL),('caf0d013-3920-4ef1-b7d3-c5f059324753','تغییرات ظاهری برنامه - css و bootstrap','ُلام. یه برنامه دارم . مشکلی نداشت. jquery و bootstrap اش رو آپگرید کردم به آخرین ورژن . به لحاظ ظاهری مشکلاتی پیدا کرده که البته زیاد...','https://cdn4.vectorstock.com/i/1000x1000/49/03/software-developer-vector-12104903.jpg',150000,'1558786622000','1558785901470',NULL),('d00fe5f6-d7f6-4dac-aba9-5a25984ae100','کار با سطرهای فایل اکسل','برنامه ای که فایلی اکسل با 3 ستون و حدود 500,000 سطر را بخواند. سپس پس از خواندن هر ستون یک سطر اگر کلمات موجود در یک سری فایل تکست (حدود4 فایل تکس...','https://cdn4.vectorstock.com/i/1000x1000/31/48/software-developer-and-programmer-vector-10673148.jpg',500000,'1558787094000','1558785301718',NULL),('d0878a8c-38dd-4060-99fc-062aeb4ec288','تقویم فارسی React JS با قابلیتهای موجود در kendo date picker','پیاده سازی کامپوننت تقویم فارسی وب برای react تحت نسخه 16. کاملا با کارکردهای تقویم kendo date picker باشد.موارد انتخاب سال و ماه، تایپ کردن تاریخ و ا','https://cdn4.vectorstock.com/i/1000x1000/31/48/software-developer-and-programmer-vector-10673148.jpg',1500000,'1558874090000','1558872601972',NULL),('d64ef337-d917-4b3c-9ad6-1a2c73bdfcb0','طراحی وب سایت و نرم افزار کتاب خوان','سلام در نظر داریم یه سرویس کتاب خوان راه اندازی نماییم. هم وب سایت و هم نرم افزارهای موبایل لطفا در مورد همکاری پیام دهید در مورد...','https://pureelementwater.com/wp-content/uploads/2017/07/proba.png',500000,'1558870859000','1558870202169',NULL),('dc32d930-d990-4372-9083-43a9b230c3a6','پروتکل ارتباطی تبادل اطلاعات بین android و پی اچ پی','سلام. یه اپ اندرویدی نوشته ایم و بحث دریافت اطلاعات از سرور هاست مونده . یه table داریم که از ستون هاش آیدی اتو انکریمنت، تاریخ و بقیه...','https://cdn.dribbble.com/users/926537/screenshots/4502924/python-2.gif',50000,'1558875927000','1558875302436',NULL),('dcafe447-a350-4b72-bb2d-141e50c792e3','یه پردازش تصویر ساده','سلام  من نرم افزاری میخوام که با لیزری که تو دستمه بتونم pointer mouse رو جابجا کنم  تصویر desktop windows    با video پروژکتور روی دیو...','https://cdn1.vectorstock.com/i/1000x1000/20/90/network-engineer-administrator-working-in-data-vector-17652090.jpg',150000,'1558780495000','1558779302017',NULL),('dcce2cd6-4548-45df-82fd-890466601d7c','ذخیره کد آفلاین جاوااسکریپت کلاینت ساید','سلام ما یک کد داریم که بر اساس فرمول یک عدد به وجود میاره   به صورت آفلاین کار میکنه ما میخواییم تغییری به وجود بیاریم که  ۱-  ای...','https://www.inetsolutions.org/wp-content/uploads/2018/11/How-to-Select-the-Correct-Web-Design-Company-for-Your-Website.png',750000,'1558787632000','1558786202441',NULL),('dd3d5688-4f81-4c02-88bf-1efb8e914ccc','تبدیل کدهای برنامه از vb به nod و خروجی گرفتن برای لینوکس','من یک اپلیکیشن دارم که تو vb طراجی شده الان می خوام از این برنامه یه خروجی بگیرم که تو لینوکس اجرا بشهمی خوام کدها  به nod.js تبدیل بشه که بتونیم خ...','https://cdn4.vectorstock.com/i/1000x1000/49/03/software-developer-vector-12104903.jpg',250000,'1558876548000','1558875902501',NULL),('e51375cb-c7c3-420c-9b9b-61087bbf195d','حل مشکلات سرور مجازی و کانفیگ','با سلام و خسته نباشید .ما یک فروشگاه اینترنتی ووکامرسی باپسوند irُسرورمجازی لینوکس ایران سرورکلود اروان داریم .نیاز به فردی با تجربه جهت کانفیگ ، بهین','https://cdn1.vectorstock.com/i/1000x1000/71/55/software-development-vector-5647155.jpg',200000,'1558877899000','1558876202425',NULL),('e77efbef-bd20-43fd-ab04-c55cf57734a7','برنامه نویسی پایتون','من مقاله ای دارم که یک شامل یکسری مدل‌های بهینه سازی ریاضی است که دوست دارم به صورت اسکریپت پایتون نوشته بشن (pyomo) که برای این منظور از هر Solver...','https://cdn.dribbble.com/users/926537/screenshots/4502924/python-2.gif',1500000,'1558782105000','1558781402420',NULL),('ee5850c7-16db-43ae-944c-f7a9e9ad7901','پروژه ساخت اپلیکیشن امــــلا*کـــــ','دوستان عزیز ابتدا کمی تعمل کنید و پروژه رو بررسی کنید بعد پیشنهاد بزنید وب سایت کار موجود و api سایت در دسترس برای طراحی پروژه سایت امــلا*کـ...','https://ponisha.ir/defaults/category-character/1.png',4000000,'1558784290000','1558782602250',NULL),('eff057cd-6bb2-46f8-8d81-1db33ed65584','آموزش ساخت بازی منچ آنلاین با یونیتی و Node.js','سلام دوستان من نیازمند ساخت یک آموزش اختصاصی هستم ، ساخت یک منچ آنلاین با Node.js و socketio که کاملا دقیق و ریز آموزش داده بشه ، میخوام خودم یاد بگیر','https://ponisha.ir/defaults/category-character/1.png',500000,'1558873558000','1558872002293',NULL),('f43574d1-6817-416f-956e-d126e0e69cbb','React js رفع مشکل','It is a small react Js project code has been written and needs correction and fixing این پروژه با ریاکت جی اس نوشته شده و در حال حاضر کار میک...','https://ponisha.ir/asset/v1/img/how-it-work/character-project.png',500000,'1558781191000','1558780202487',NULL),('f73ab135-92c8-44fa-a33f-08e2e3631f49','دریافت اطلاعات کامل یک سایت فروشگاهی','سلام پروژه درخواستی به این صورت هست که اطلاعات کامل یک سایت که شامل 100 هزار عنوان کتاب هستش رو باید خروجی بگیرید. اطلاعات باید به صورت ذخیره...','https://ponisha.ir/asset/v1/img/how-it-work/character-project.png',500000,'1558785991000','1558784702453',NULL),('faee1c07-68a8-432f-a11f-df3ecc35aae4','طراحی جهت رزرو یک یا چند صندلی','تعریف تسک : \n رزرو یک یا چند صندلی طبق مستندات اعلام شده.\n نیازمندی ها 3.x – Java Script\n نحوه چیدمان صندلی •','https://www.inetsolutions.org/wp-content/uploads/2018/11/How-to-Select-the-Correct-Web-Design-Company-for-Your-Website.png',150000,'1558868529000','1558867202223',NULL),('fcc07e17-1fef-4a7b-a2e0-cc8eab57001c','طراحی فرم بیمه مانند بیمیتو با ری اکت','بنده نیاز دارم امکانات شبیه سایت بیمیتو داشته باشم. فرم بیمه+فرم یادآوری','https://ponisha.ir/asset/v1/img/how-it-work/character-contest.png',1000000,'1558782835000','1558781102525',NULL),('fccf8e82-9b24-437a-be62-b106715c7c52','ذخیره کد آفلاین جاوااسکریپت کلاینت ساید','سلام ما یک کد داریم که بر اساس فرمول یک عدد به وجود میاره   به صورت آفلاین کار میکنه ما میخواییم تغییری به وجود بیاریم که  ۱-  ای...','https://cdn1.vectorstock.com/i/1000x1000/71/55/software-development-vector-5647155.jpg',750000,'1558787850000','1558786502166',NULL),('fd0c23c7-564e-4516-b9f9-b3200fe4168a','توسعه حلگر نانوسیال مغناطیسی','نرم افزار اپن فوم (openFoam) از جمله نرم افزارها در تحلیل جریان سیال می‌باشد و در مهندسی مکانیک شاخه تبدیل انرژی کاربرد زیادی دارد. این نرم افزار دارا','https://ponisha.ir/asset/v1/img/how-it-work/character-contest.png',6000000,'1558875408000','1558873802205',NULL),('fd6295a3-c906-40fd-adf9-0cba5343d1e0','طراحی نرم افزارویرایش متن ساده با زبان جاوا به همراه دیتابیس','با سلام. \n\nمن یک برنامه تحت ماشین جاوا میخوام که به اون یک پاراگراف(انگلیسی) رو بدم و نرم افزار با داده کاوی متن، کلماتی که به دیتابیسش معرفی...','https://recruitingdaily.com/wp-content/uploads/sites/6/2018/04/28-year-old-software-engineer.jpg',750000,'1558879345000','1558877702292',NULL),('ffe152b4-1220-43ac-bf99-7620099e647c','اپ ساده','ساخت اپلیکیشن موبایل اندرویدی که:1. قابلیت بروزرسانی داشته باشد 2. از خدمات رایگان پول بسازد 3. ظاهر جذاب و کارکرد ساده و صحیح برای مشت...','https://www.inetsolutions.org/wp-content/uploads/2018/11/How-to-Select-the-Correct-Web-Design-Company-for-Your-Website.png',500000,'1558784947000','1558783801876',NULL);
/*!40000 ALTER TABLE `Project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProjectSkill`
--

DROP TABLE IF EXISTS `ProjectSkill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ProjectSkill` (
  `skillName` char(150) NOT NULL,
  `projectId` char(150) NOT NULL,
  `points` int(11) DEFAULT NULL,
  PRIMARY KEY (`skillName`,`projectId`),
  KEY `projectId` (`projectId`),
  CONSTRAINT `projectskill_ibfk_1` FOREIGN KEY (`skillName`) REFERENCES `skill` (`name`),
  CONSTRAINT `projectskill_ibfk_2` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectSkill`
--

LOCK TABLES `ProjectSkill` WRITE;
/*!40000 ALTER TABLE `ProjectSkill` DISABLE KEYS */;
INSERT INTO `ProjectSkill` VALUES ('Android','326a7390-685f-4059-abdb-7f397d36c2c3',1),('Android','39d15a36-a5e7-47a2-9abf-9624efd37e4f',3),('Android','5a18806d-7341-461b-a96d-33b8c8c776e7',2),('Android','98469025-5b1b-4aad-837b-08033e908dd3',2),('Android','9e57696a-e70e-417e-9bb0-aa78127ed684',3),('Android','a632047f-3431-4604-8b7a-37c97d4c33bb',3),('Android','b8ddfa5e-a929-4590-8ff2-f507ccafa460',3),('Android','d64ef337-d917-4b3c-9ad6-1a2c73bdfcb0',2),('Android','dc32d930-d990-4372-9083-43a9b230c3a6',1),('Android','ee5850c7-16db-43ae-944c-f7a9e9ad7901',3),('Android','ffe152b4-1220-43ac-bf99-7620099e647c',2),('C','193e22ba-4318-4865-af93-8bd54818d73b',2),('C','984a85a7-d95f-4763-9784-055fdda595d1',3),('C++','193e22ba-4318-4865-af93-8bd54818d73b',1),('C++','399e93bc-67c6-4dff-b232-1dd8da9a3dcb',1),('C++','984a85a7-d95f-4763-9784-055fdda595d1',1),('C++','fd0c23c7-564e-4516-b9f9-b3200fe4168a',3),('CSS','259a09f5-6f11-40d7-b7d4-39aee5d5e2e0',1),('CSS','311b4ac7-34a1-4b72-baa3-dd9ba6654d42',3),('CSS','3142c933-2fcf-4e74-92f6-fe9ff02e905f',1),('CSS','805d80d5-caff-4aa2-a520-605610d668ce',2),('CSS','caf0d013-3920-4ef1-b7d3-c5f059324753',1),('CSS','faee1c07-68a8-432f-a11f-df3ecc35aae4',1),('Django','02b73af0-69f0-4ea1-ba52-18762513b564',2),('Django','75beb32b-dae9-4ba7-ae2c-709912a17699',1),('Django','d64ef337-d917-4b3c-9ad6-1a2c73bdfcb0',2),('HTML','259a09f5-6f11-40d7-b7d4-39aee5d5e2e0',1),('HTML','311b4ac7-34a1-4b72-baa3-dd9ba6654d42',1),('HTML','3142c933-2fcf-4e74-92f6-fe9ff02e905f',2),('HTML','805d80d5-caff-4aa2-a520-605610d668ce',1),('HTML','8d173722-24ef-4c76-bf78-72c35c5dd477',1),('HTML','9bd919fa-b78f-422f-82f9-861092f5eb19',2),('HTML','dcce2cd6-4548-45df-82fd-890466601d7c',1),('HTML','f43574d1-6817-416f-956e-d126e0e69cbb',3),('HTML','faee1c07-68a8-432f-a11f-df3ecc35aae4',2),('HTML','fccf8e82-9b24-437a-be62-b106715c7c52',3),('Java','12ff517b-fe42-4aff-928e-51020c2c66bf',3),('Java','193e22ba-4318-4865-af93-8bd54818d73b',2),('Java','1b995d2b-d2a5-403f-8254-11463fdcb1c4',3),('Java','1c771581-ff31-4cb2-b6af-3844bde2c759',1),('Java','21ab7c2d-6bde-4899-b9ad-dfeffd3c9491',2),('Java','3435421f-a136-40d4-b14e-e9e4e1fbce03',2),('Java','39d15a36-a5e7-47a2-9abf-9624efd37e4f',3),('Java','5a18806d-7341-461b-a96d-33b8c8c776e7',3),('Java','60a7dee4-2565-49c9-b8aa-d058c428f5a5',3),('Java','73555575-99a1-4f67-8504-b1fa09914b01',2),('Java','74e4f840-72dd-469e-8e9b-bd35b87b4738',1),('Java','788ed75a-6229-4015-a3ec-9cbe4974c698',1),('Java','7ac9d1be-18c1-4d63-a7e6-31e5b119f234',2),('Java','807133a8-b32b-4564-a24f-92110bfef3a6',3),('Java','8ef8d89d-3ce0-4fd0-b37e-ac1cca5a4837',3),('Java','9721e658-b883-4119-87ee-28989d0e6048',1),('Java','984a85a7-d95f-4763-9784-055fdda595d1',2),('Java','9e57696a-e70e-417e-9bb0-aa78127ed684',2),('Java','9fd02c1b-93d2-4dbe-9431-a3221253a75d',1),('Java','b8ddfa5e-a929-4590-8ff2-f507ccafa460',1),('Java','dc32d930-d990-4372-9083-43a9b230c3a6',2),('Java','ee5850c7-16db-43ae-944c-f7a9e9ad7901',1),('Java','f73ab135-92c8-44fa-a33f-08e2e3631f49',3),('Java','fd6295a3-c906-40fd-adf9-0cba5343d1e0',1),('Javascript','259a09f5-6f11-40d7-b7d4-39aee5d5e2e0',2),('Javascript','311b4ac7-34a1-4b72-baa3-dd9ba6654d42',2),('Javascript','3142c933-2fcf-4e74-92f6-fe9ff02e905f',2),('Javascript','5a18806d-7341-461b-a96d-33b8c8c776e7',2),('Javascript','75beb32b-dae9-4ba7-ae2c-709912a17699',1),('Javascript','7ac9d1be-18c1-4d63-a7e6-31e5b119f234',1),('Javascript','82429dc1-58bf-4344-97a6-49b71b9c1a6c',1),('Javascript','caf0d013-3920-4ef1-b7d3-c5f059324753',2),('Javascript','d0878a8c-38dd-4060-99fc-062aeb4ec288',3),('Javascript','dcce2cd6-4548-45df-82fd-890466601d7c',3),('Javascript','ee5850c7-16db-43ae-944c-f7a9e9ad7901',3),('Javascript','eff057cd-6bb2-46f8-8d81-1db33ed65584',3),('Javascript','f43574d1-6817-416f-956e-d126e0e69cbb',1),('Javascript','faee1c07-68a8-432f-a11f-df3ecc35aae4',3),('Javascript','fccf8e82-9b24-437a-be62-b106715c7c52',3),('Javascript','ffe152b4-1220-43ac-bf99-7620099e647c',1),('Linux','193e22ba-4318-4865-af93-8bd54818d73b',2),('Linux','728436cf-4d5c-408a-8b6f-5e950dfc4ef1',2),('Linux','984a85a7-d95f-4763-9784-055fdda595d1',3),('Linux','af8a9c7b-14ab-4500-8fef-db9baca0f56f',2),('Linux','e51375cb-c7c3-420c-9b9b-61087bbf195d',2),('MySQL','74535fff-dec2-40e5-b409-9efe183b78c1',2),('MySQL','903313ce-4b45-4483-bcd9-dc7f41d3d3a6',1),('MySQL','dc32d930-d990-4372-9083-43a9b230c3a6',2),('Node.js','02b73af0-69f0-4ea1-ba52-18762513b564',3),('Node.js','193e22ba-4318-4865-af93-8bd54818d73b',1),('Node.js','1a42dad7-e615-41f2-95ea-f20bc32de639',2),('Node.js','788ed75a-6229-4015-a3ec-9cbe4974c698',2),('Node.js','82429dc1-58bf-4344-97a6-49b71b9c1a6c',1),('Node.js','984a85a7-d95f-4763-9784-055fdda595d1',2),('Node.js','9bd919fa-b78f-422f-82f9-861092f5eb19',1),('Node.js','9fd02c1b-93d2-4dbe-9431-a3221253a75d',2),('Node.js','a632047f-3431-4604-8b7a-37c97d4c33bb',1),('Node.js','af8a9c7b-14ab-4500-8fef-db9baca0f56f',3),('Node.js','d64ef337-d917-4b3c-9ad6-1a2c73bdfcb0',1),('Node.js','dcce2cd6-4548-45df-82fd-890466601d7c',2),('Node.js','dd3d5688-4f81-4c02-88bf-1efb8e914ccc',3),('Node.js','eff057cd-6bb2-46f8-8d81-1db33ed65584',2),('Node.js','fccf8e82-9b24-437a-be62-b106715c7c52',3),('Photoshop','7f96c618-b4bc-4954-b066-c1a0d5a35f75',2),('PHP','02b73af0-69f0-4ea1-ba52-18762513b564',3),('PHP','15e23ee7-44be-487a-94da-6a1b8169e5f5',1),('PHP','1b995d2b-d2a5-403f-8254-11463fdcb1c4',1),('PHP','1c771581-ff31-4cb2-b6af-3844bde2c759',2),('PHP','21ab7c2d-6bde-4899-b9ad-dfeffd3c9491',3),('PHP','259a09f5-6f11-40d7-b7d4-39aee5d5e2e0',3),('PHP','75beb32b-dae9-4ba7-ae2c-709912a17699',2),('PHP','788ed75a-6229-4015-a3ec-9cbe4974c698',2),('PHP','7ac9d1be-18c1-4d63-a7e6-31e5b119f234',2),('PHP','8d173722-24ef-4c76-bf78-72c35c5dd477',1),('PHP','8ef8d89d-3ce0-4fd0-b37e-ac1cca5a4837',3),('PHP','903313ce-4b45-4483-bcd9-dc7f41d3d3a6',1),('PHP','9721e658-b883-4119-87ee-28989d0e6048',3),('PHP','9bd919fa-b78f-422f-82f9-861092f5eb19',3),('PHP','9fd02c1b-93d2-4dbe-9431-a3221253a75d',1),('PHP','a632047f-3431-4604-8b7a-37c97d4c33bb',3),('PHP','af8a9c7b-14ab-4500-8fef-db9baca0f56f',3),('PHP','d64ef337-d917-4b3c-9ad6-1a2c73bdfcb0',3),('PHP','dc32d930-d990-4372-9083-43a9b230c3a6',2),('PHP','f43574d1-6817-416f-956e-d126e0e69cbb',1),('PHP','f73ab135-92c8-44fa-a33f-08e2e3631f49',2),('Python','15e23ee7-44be-487a-94da-6a1b8169e5f5',3),('Python','21ab7c2d-6bde-4899-b9ad-dfeffd3c9491',3),('Python','399e93bc-67c6-4dff-b232-1dd8da9a3dcb',2),('Python','3a35f6a1-3f5e-4318-beee-a9c65e680cf2',3),('Python','5d7696ec-a1bc-4584-bceb-708fdd8137c8',3),('Python','60a7dee4-2565-49c9-b8aa-d058c428f5a5',2),('Python','74535fff-dec2-40e5-b409-9efe183b78c1',1),('Python','75beb32b-dae9-4ba7-ae2c-709912a17699',3),('Python','788ed75a-6229-4015-a3ec-9cbe4974c698',2),('Python','807133a8-b32b-4564-a24f-92110bfef3a6',3),('Python','8d173722-24ef-4c76-bf78-72c35c5dd477',2),('Python','903313ce-4b45-4483-bcd9-dc7f41d3d3a6',2),('Python','9d3ee0a2-234e-4c17-bff5-594512ab7569',1),('Python','9fd02c1b-93d2-4dbe-9431-a3221253a75d',2),('Python','bc1336a6-4951-433a-b7ac-5cfa91e7049d',1),('Python','d00fe5f6-d7f6-4dac-aba9-5a25984ae100',2),('Python','dcafe447-a350-4b72-bb2d-141e50c792e3',1),('Python','e77efbef-bd20-43fd-ab04-c55cf57734a7',3),('Python','fd0c23c7-564e-4516-b9f9-b3200fe4168a',2),('React','311b4ac7-34a1-4b72-baa3-dd9ba6654d42',2),('React','faee1c07-68a8-432f-a11f-df3ecc35aae4',2),('React','fcc07e17-1fef-4a7b-a2e0-cc8eab57001c',2),('SEO','0e240cfc-0638-441d-89bf-e11256acf3d0',1),('SEO','392e09f3-e3ec-4051-9634-6181fb444ce9',1),('SEO','c49a5afd-c7ba-4bf3-97d4-6dcf68eaca96',3),('SQL','0e240cfc-0638-441d-89bf-e11256acf3d0',3),('SQL','392e09f3-e3ec-4051-9634-6181fb444ce9',2),('SQL','615545b3-7666-4416-a3ce-7f062e0626b0',3),('SQL','73555575-99a1-4f67-8504-b1fa09914b01',2),('SQL','86be9e94-e227-4a87-867c-d4cedb551e5c',2),('SQL','c49a5afd-c7ba-4bf3-97d4-6dcf68eaca96',3),('SQL','fd6295a3-c906-40fd-adf9-0cba5343d1e0',1);
/*!40000 ALTER TABLE `ProjectSkill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Skill`
--

DROP TABLE IF EXISTS `Skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Skill` (
  `name` char(150) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Skill`
--

LOCK TABLES `Skill` WRITE;
/*!40000 ALTER TABLE `Skill` DISABLE KEYS */;
INSERT INTO `Skill` VALUES ('Android'),('C'),('C++'),('CSS'),('Django'),('HTML'),('Java'),('Javascript'),('Linux'),('MySQL'),('Node.js'),('Photoshop'),('PHP'),('Python'),('React'),('SEO'),('SQL');
/*!40000 ALTER TABLE `Skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `User` (
  `username` char(10) NOT NULL,
  `password` char(100) DEFAULT NULL,
  `firstName` char(150) DEFAULT NULL,
  `lastName` char(150) DEFAULT NULL,
  `jobTitle` char(250) DEFAULT NULL,
  `bio` char(250) DEFAULT NULL,
  `profilePictureURL` char(200) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserSkill`
--

DROP TABLE IF EXISTS `UserSkill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `UserSkill` (
  `userId` char(150) NOT NULL,
  `skillName` char(150) NOT NULL,
  PRIMARY KEY (`userId`,`skillName`),
  KEY `skillName` (`skillName`),
  CONSTRAINT `userskill_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`username`),
  CONSTRAINT `userskill_ibfk_2` FOREIGN KEY (`skillName`) REFERENCES `skill` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserSkill`
--

LOCK TABLES `UserSkill` WRITE;
/*!40000 ALTER TABLE `UserSkill` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserSkill` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-26 18:37:44
