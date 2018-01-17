import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by neo on 29/09/2017.
 */
public class CommentImport {
    public static void main(String[] args) throws Exception {
//        printImport();
        printUpdate();
    }

    private static void printUpdate() {
        for (int i = 475; i < 843; i++) {
            String linUp = "UPDATE `shopper`.`product_comment` SET `uid`='1' WHERE `id`> 474;\n";
//            linUp = "DELETE FROM `shopper`.`product_comment` WHERE `id`='" + i + "';\n";
            System.out.print(linUp);
        }
    }

    private static void printImport() throws Exception {
        String lines = getStr();
        String[] lin = lines.split("\n");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        for (String l : lin) {
            String[] words = l.split("\t");
            String pid = words[0];
            String name = words[1];
            double star = Double.parseDouble(words[2]);
            String html = words[3].replaceAll("\"", "\\\"").replaceAll("\'","\\\\'");
            String time = words[4];
            Date date = format.parse(time);
            time = format2.format(date);
//            System.out.print(pid + "\t");
//            System.out.print(name + "\t");
//            System.out.print(star + "\t");
//            System.out.printf("\t%14s\t\t", time);
//            System.out.print(html + "\t");
//            System.out.println();
            String module = "INSERT INTO `shopper`.`product_comment` (`pid`,`uid`, `name`, `star`, `content_html`,`create_date`) VALUES ('" + pid + "', '0', '" + name + "', '" + star * 20 + "', '" + html + "', '" + time + "' );\n";
            System.out.print(module);
        }
//        HashMap<Integer, Module> map = new HashMap<>();
//        for (String l : lin) {
//            String[] words = l.split("\t");
//            String pid = words[0];
//            int star = (int) Double.parseDouble(words[2]);
//            Module module = map.get(Integer.parseInt(pid));
//            if (module == null) {
//                module = new Module();
//                map.put(Integer.parseInt(pid), module);
//            }
//            switch (star) {
//                case 0:
//                case 1:
//                    module.star1 += star;
//                    break;
//                case 2:
//                    module.star2 += star;
//                    break;
//                case 3:
//                    module.star3 += star;
//                    break;
//                case 4:
//                    module.star4 += star;
//                    break;
//                case 5:
//                    module.star5 += star;
//                    break;
//            }
//            module.starTotal += 1;
//            double totalScore = module.score * (module.starTotal - 1);
//            module.score = ((totalScore + star * 20) / module.starTotal);
//        }
//
//        for (Map.Entry<Integer, Module> entry : map.entrySet()) {
//            Module module = entry.getValue();
//            int pid = entry.getKey();
//            String m = "UPDATE `shopper`.`product` SET `star1_num`='" + module.star1 + "', `star2_num`='" + module.star2 + "', `star3_num`='" + module.star3 + "', `star4_num`='" + module.star4 + "', `star5_num`='" + module.star5 + "', `stars_num`='" + module.starTotal + "', `stars`='" + module.score + "' WHERE `pid`='" + pid + "';\n";
//            System.out.print(m);
//        }

    }

    public static String getStr() {
        String lines =
                "30\tMs.**a\t5\tEasy to use. Plenty of guts.\t21/08/2017 8:33\n" +
                        "30\tMa***k\t5\tthe price is cheap, quality is good\t22/08/2017 10:53\n" +
                        "30\tDa***l\t5\tsupports 4k tv, it\'s cool\t22/08/2017 16:58\n" +
                        "30\tAn***s\t4\tno buffers, it will be great if the memory could b 2+8\t05/09/2017 13:24\n" +
                        "30\tGi***e\t5\tFive Stars\t12/09/2017 17:55\n" +
                        "30\tTT***r\t5\tIt has been pretty great so far although the memory is super low. Would recommend to anyone\t18/09/2017 23:16\n" +
                        "30\tzd***m\t5\tA very nice product for the price\t24/09/2017 19:45\n" +
                        "32\tst***s\t5\tso mini, so cute\t23/07/2017 16:31\n" +
                        "32\t Ru***m\t5\t2/8gb, not bad\t16/08/2017 23:37\n" +
                        "32\t CO***S\t4\tEverything played back smooth and getting around in it was fairly quick. I recommend getting a mini keyboard to use it, it\'s easier when it comes to typing and most have a little mousepad built into it which also makes it easier to get around the OS. \t24/08/2017 7:55\n" +
                        "32\tAna***\t5\tWe are still exploring the new channels everyday, but we are very happy with it so far. Highly recommend it!\t13/09/2017 21:24\n" +
                        "32\t Iv***l\t5\tThe 2Gb memory is really cool, it boost your online performance and reduces buffering. It works great on both Ethernet and Wi-Fi, I just wish that the Wi-Fi was 5Ghz. You\'ll love this Leelbox mini LoL\t20/09/2017 12:11\n" +
                        "32\tdz***a\t5\t I stream youtube a lot with it and so far it works great. Definitely worth the price.\t22/09/2017 17:20\n" +
                        "32\tев***о\t5\tThanks to 2GB Ram, the system is super fast and smooth. Compared to my old boxes, it also has Bluetooth and 5G wireless network.\t22/09/2017 19:41\n" +
                        "32\tJen**o\t5\tHoly crap this box is amazing\t25/09/2017 15:22\n" +
                        "31\tKay**e\t5\tworks. Bought as a replacement for one I use ...\t30/06/2017 8:30\n" +
                        "31\tPai**i\t4.5\tGood product and Kodi works well on it. The only reason I gave it 4 stars and not 5 is that the remote,I don\'t like the design.Other than that I am happy with it and am glad I purchased it.\t30/07/2017 21:40\n" +
                        "31\tAn**i\t5\tQ2 Pro is easy to use and very entertaining. I recommend getting the keypad/qwerty remote additional for easy use.\t05/09/2017 16:31\n" +
                        "31\tJ.***o \t5\tI did my research and found this product which has great reviews and decided to give it a try. I was able to set it up and started watching moves pretty quickly. There are a lot of youtube video that shows how to set up the device and it was pretty easy to follow the steps. So far so good but I only give 4.5 stars for didn\'t get discount.\t05/09/2017 23:37\n" +
                        "31\tSe***a \t5\tThis box works great with no lag. I did upgrade from kodi 16 to kodi 17, So I could down load the build I wanted the build that came on it had no sports.\t10/09/2017 7:55\n" +
                        "31\tKe***s\t5\tThis box is great and it\'s even better for the price, it has all the top qualities you would want when looking for a tv box, I\'ve personally owned around 3 to 4 tv boxes and this stands up to any of them.\t13/09/2017 21:24\n" +
                        "31\tBr***n\t4\tnot bad.\t15/09/2017 8:41\n" +
                        "31\tKe***s\t5\tThe device already loaded with many great app out of the box, so I didn\'t need to download by myself, it\'s cool, this is my first box, I am learning to use it.\t16/09/2017 3:21\n" +
                        "31\tRo***n\t5\teverything is fine, but with a keyboard it will be more easier to handle\t19/09/2017 13:12\n" +
                        "31\tNo***m\t5\tBox works as described. I haven\'t tested any HD content as of yet. \t20/09/2017 17:34\n" +
                        "31\tCr***L\t5\tThe product that they sent got some minor problems but the customer service responded so fast to make up for it.5 stars for the product,6 stars for the customer service.\t23/09/2017 19:44\n" +
                        "31\tfay****@hotmail.com\t5\t The TV box itself is very small, small enough to bring around when go on a trip. It packs with all the inputs and outputs I need, hdmi,couple usbs and micro sd slot. The device already loaded with many great app out of the box. So far it supported all the apps that I downloaded from google store and from third party apk website. The streaming is smooth and picture quality is amazing. The Bluetooth feature was great. I was able paired my headphone easily and sound is on sync. Highly reccomended.\t23/09/2017 9:52\n" +
                        "33\tLu***\t5\tI got this to replace my very old Roku Player and I was not disappointed. I have been experiencing problem with my Roku and I thought it was time for a change. I made the right choice. There is no lag time and no buffering even at 1080i which is even better than playing it in my computer. Also the customer service is fantastic! \t26/06/2017 8:30\n" +
                        "33\tEr***t\t5\tMuch faster processor and menu speeds. Didn\'t use the instructions that come with it, just set it up the same way as every other android box and was up and running in minutes.\t15/07/2017 21:40\n" +
                        "33\tMa***n\t5\tAwesome. \t30/07/2017 16:31\n" +
                        "33\tAl***o\t5\tDid everything It expected it to do.\t01/08/2017 23:37\n" +
                        "33\tAl***o\t5\tIncredible Android TV box with very little to do to get it setup and start enjoying your favorites movies. I was surprised to see that the included HDMI cables are high end and not cheap cables like that are included in the other boxes that I have purchased. \t03/08/2017 7:55\n" +
                        "33\tTa***r\t5\tDual wifi is really cool. I am using this in my upstairs guest bedroom and my router is located on the first floor. It is showing full signal and has no lag when playing 1080p movies. \t13/09/2017 21:24\n" +
                        "33\tma***2\t5\talthough it is a little expensive than others, it worth the price, very good experience so far\t20/09/2017 8:41\n" +
                        "50\tKo***n\t5\tA very fast streamer with a great remote.\t01/07/2017 13:12\n" +
                        "50\tKA***S\t5\tGreat box, works as promised.I liked the clean interface, so nice not loaded with junk!\t01/07/2017 16:56\n" +
                        "50\tSz***h\t5\tWorks great it is the second tv box I\'ve purchased and streams smoother and the picture quality is very good\t03/07/2017 21:25\n" +
                        "50\tron***\t4\tIts OK... do research on others before grabbing this one\t15/07/2017 8:39\n" +
                        "50\tДе***в\t5\tNot only a tvbox, also a music center, a game station, even a wifi router! It comes with a mini keypad as well, it makes me feel like a blackberry, you guys surely know that means you can do quite a lot on it. Feel so cool, discovering more...\t15/07/2017 23:12\n" +
                        "50\tAP***A\t5\tThis tv box works great than I ever expected.keyboard is one of the best features,efficient easy to operate.it is a great buy..\t16/07/2017 13:12\n" +
                        "50\tKi***а\t5\tGreat Box and the great seller answered a question on where to find the dongle (in the battery compartment) very quickly and even sent a photo of where it was located. \t18/07/2017 17:34\n" +
                        "50\tNa***n\t4\tI had to upgrade from 17.0 to 17.3 then 17.4 to get this box working right. I only use the one remote. The mini key keyboard is a little small for my purpose. All in all this is a good device.\t22/07/2017 16:31\n" +
                        "50\tDa***s\t5\tthe fourth tv smart box I\'ve worked with, by far the best so far that I have used in the sub one hundred dollars price point.\t27/07/2017 23:37\n" +
                        "50\tza***m\t5\tIt is a great alternative for cable and or satellite tv! Will it will take you a small amount of time to understand how to use it, I\'m very happy with it! You will need a higher speed internet connection, but not necessarily the highest speed!!! \t05/08/2017 7:55\n" +
                        "50\tbi***o\t4\tVery fast and nice interface.\t09/08/2017 21:24\n" +
                        "50\tGe***o\t5\t2/16g is cool, very fast\t11/08/2017 8:41\n" +
                        "50\tri***o\t5\tThis box does eliminate the buffering, pausing, and skipping that I was dealing with on my firestick. The tiny keyboard takes some getting used to and you do have to update the version of Kodi this has on it. \t14/08/2017 3:21\n" +
                        "50\tne***t\t5\tDecent price with such a good quality Tv Box!I love the keyboard very much, very good hand feeling\t16/08/2017 13:12\n" +
                        "50\tma***e\t5\torder for my parents, they love it but didn\'t know how to use, I will teach them\t21/08/2017 16:56\n" +
                        "50\tki***l\t5\tI have had the Kingbox for a few days and still learning how to use, it is so amazing every day i will found i love it more and moreLove it , easy to use , easy setup , very much impressed. Very happy with the Kingbox, two remotes is so cool!\t22/08/2017 21:25\n" +
                        "50\tfa***y\t5\tConnect the hdmi cable to the TV, plug power in and done! I had to do some research on what apps I\'d like to install, but again, installing app is also quite easy. \t12/09/2017 8:39\n" +
                        "50\tda***j\t4\tThe TV box is in a great quality but I wish I could buy this in a great deal.\t18/09/2017 23:12\n" +
                        "50\tHU***D\t5\tWe tried different kinds of TV boxes before we got this one. This one exceeded all our exceptions. It is easy to set up, we hook it up to our TV in a minute. It is really a plug and play device. Comes with android 6.0 already I definitely recommend this product to anyone who is searching for a TV box.\t19/09/2017 13:12\n" +
                        "50\tRB***h\t5\tThe box is NO buffering when watching movies on Kodi.5 Stars\t21/09/2017 17:34\n" +
                        "50\tpp***A\t5\t User friendly dashboard. The min keyboard is supper useful\t23/09/2017 19:44\n" +
                        "50\tqi***o\t5\tLove ittttt....\t25/09/2017 9:52\n" +
                        "36\toi***s\t5\tGreat product you can connect with WiFi or Ethernet to get internet\t29/08/2017 8:30\n" +
                        "36\tga***t\t5\tOne thing that stands out is that the remote for this is very good and the box response sharply to the button commands. As you can see from the pictures I included the box is very small so it does not stick out. The blue light is not too bright either. Even though the CPU is not a high end one it does well with loading video\'s and apps. It comes loaded with its own version of Kodi 17.3 but I went ahead and updated it to 17.4. Out of the box it has a bunch of repositories included so if your not savvy with these boxes there is no worry, you can click and go! One feature I didn\'t realize was missing when I purchased was Bluetooth. Not a big deal for me as I had a specific use for this.\t31/08/2017 11:15\n" +
                        "36\tna***y\t5\tGood Box for the price! 5 of these in my home. Picks up WiFi really well and Ethernet even better!\t03/09/2017 14:06\n" +
                        "36\twe***n\t5\tI installed the Plex on this box and mounted it back of my Samsung tv, and connected the Plex to my Plex server, and I can watch everything I can in 4K resolution. and I don\'t have any cable dangling around as everything in on back of the TV. I would recommend this system to anyone who need it.\t08/09/2017 18:35\n" +
                        "36\tSh***g\t5\tAwesome Android TV Box，Did everything it expected to do.\t10/09/2017 21:03\n" +
                        "36\tsu***s\t5\tSame day received, same day hooked up. Instantly I watched what ever I wanted,2 movies and many sport channels.All in all a very good product at a reasonable price that will be used a lot. Going to order another one for my bedroom!\t19/09/2017 9:31\n" +
                        "39\tal***w\t5\tThis thing is simply amazing.Very easy to install,it shown beautiful hd pictures and very fast when using,almost no buffering and freezing. Working great,I loved it.will share it with my friends.\t29/07/2017 8:40\n" +
                        "39\tev***n\t5\tOutstanding Android TV box , highly recommend it! <br/>" +
                        "I love this android box! Because of I use it for watching TV paly games download apps......<br/>" +
                        "the r1 box support connect 64tb hard drive and my air mouse the same time,so that i can share my own movies and photos with my family and friends.<br/>" +
                        "it is easy to take the TV box into any of my rooms with a TV and just need connect it to the cables and wifi.super portable!\"\t11/08/2017 11:35\n" +
                        "39\tWi***n\t5\tAmazing r1 player,Browser and youtube work without problems. I took the box connect with my air mouse so the remote control it\'s very convenient to work in the browser. Great! I am very happy with my box.\t03/09/2017 14:06\n" +
                        "39\tuu***s\t5\tEnjoying it. It\'s more better than Amazon fire stick which I have used. no problem to setting up and you can watch play anything...I think it\'s worth. A+++\t14/09/2017 18:54\n" +
                        "39\tpt***p\t5\t\"I would recommend this box to my friends and family. really a great Android product.<br/>" +
                        "I have tried a lot of different boxes and this one is the most user friendly one I have found. It\'s amazing mini size and easy to plug for use. I bought a second unit for my living room TV.\"\t19/09/2017 4:20\n" +
                        "39\tmi***e\t5\tWhen my old WDTV box was getting a bit long in the tooth, I looked round for a suitable replacement. This tv box for me as it\'s future-proofed for 4K and can run apps such as YouTube, BBC iPlayer, Kodi and VLC. So that covers most of my viewing methods be they streaming . Pretty easy to set up and use, I\'m very happy with it.\t21/09/2017 13:54\n" +
                        "39\tvo***l\t5\t\"WOO!!!!!This tv box is really convenient, can watch stuff that the phone didn\'t even update yet , it\'s really convenient<br/>" +
                        "This is my first Kodi box and I definitely recommend it. The instruction book is a little sparse but the box was very easy to set up and works well. The picture quality is excellent.\"\t23/09/2017 9:28\n" +
                        "39\tuh***e\t5\tSuper excited with my purchase! My boys want access to movies, etc, in their room but I refused to pay the additional fees to able them to our cable bill. This box has solved our problem! They can watch Netflix and even local channels without me having to pay a monthly fee. Well worth the money!\t24/09/2017 18:20\n" +
                        "39\tPl***a\t4\tGood box!\t28/09/2017 2:31\n" +
                        "46\tLI***s\t5\tProduct is easy to set up and use and came with everything needed to work straight out of the box once batteries were put in the remote control. All on screen menus are fairly straight forward to understand as is the settings area. The picture is good as well, all in i would say it is very good value for money and very fast, well packaged delivery was an added bonus as well so its 5 stars all round from me.\t13/07/2017 14:10\n" +
                        "46\tji***o\t5\t have not had time to download any new apps and dig in to see what are the scope of its capabilities. But I have already used two TV app and watched most all channels, very similar to line up of my cable channels like CNN, Natgeo, BBC. It also has a browser which I have used. You can download FB or YouTube or play games.\t24/07/2017 16:59\n" +
                        "46\tVi***b\t5\tMy projector became a projector immediately. I thought it\'s like a lunchbox size, the old TV cable. When I got the package, I didn\'t recognize what I bought can be fit the tiny mailbox. The HDMI cable is very thick better than I bought before. The manual tells me it\'s easy to set up. There are two USB ports, I use one port for the Bluetooth mouse. Everything is similar to an Android pad, I have no problem to use it. It has 8 GB storage already, downloading apps from Google store is smoothy. I couldn\'t find Lan cable, but it connects to wifi stable.\t31/07/2017 7:30\n" +
                        "46\tta***z\t5\tExcellent device that was easy to install and setup,Works awesome.\t06/08/2017 14:10\n" +
                        "46\tRt***u\t5\tLove this\t12/08/2017 3:32\n" +
                        "46\tbt***s\t4\tI can now watch all my movies in the tv without having to switch my firestick back and forth，great product。\t18/08/2017 8:21\n" +
                        "46\tyi***w\t5\tWe got this for our daughters bedroom so she can watch Netflix and YouTube videos. This was easy to hook up, took about 5 minutes. It did do an update when we powered it on. So far everything works on it with no issues. The remote works great from a distance. Our daughter is happy to be able to watch stuff in her bedroom now.\t23/08/2017 14:48\n" +
                        "46\tLa***u\t5\tGreat Buy!\t26/08/2017 15:54\n" +
                        "47\tji***x\t5\tAmazing TV box. Everything worked well. I have used it more than one week, just as I expected. It fit very easy , connect WIFI is ok, and operation is also easy. Just as instruction, simple as your android phone. And I can surf the Internet and enjoy movie. I love it. Worth for the price.\t28/08/2017 5:40\n" +
                        "47\tKo***u\t5\tAbsolutely LOVE this product. Basically plug and play. I had a MDQ box and this one was much simpler to navigate. Seems like there are more movie selections but much easier to find as well. I like how small this box is - you can just unplug it and take it with you when you travel. Well worth the price.\t02/09/2017 4:31\n" +
                        "47\thi***p\t5\tExcellent! Got it in two days as promised, quick and easy to install.It was working within two minutes, fast and great picture. Way beyond my expectations! Would recommend anyone to purchase this, just wish it had more variety of colors to chose from, other than that it\'s excellent. Works well compared to others. \t09/09/2017 15:32\n" +
                        "47\tma***c\t5\tConnects right away when I need it. So far the touch pad works great. I have not used every key but when I need to type something it works great. I would recommend this to anyone who needs a mouse for thier TV box.\t14/09/2017 7:40\n" +
                        "47\tcu***d\t4\tOkay box, have to clear your cache often and force stop apps frequently. Okay for the price.\t17/09/2017 18:10\n" +
                        "44\tfi***g\t5\tWorks perfectly! I love it!\t02/07/2017 22:20\n" +
                        "44\tot***i\t5\tI have kids that dont like to keep thier hands off of things that are not meant for them to play with. (I.E. dads wireless keyboard) I went with a cheaper model so when they inevitably stomp on it, drop it, or spill something on it I wont be too heartbroken. Setup was easy... Plug in the usb dongle, remove the little tags from the surprise included batteries, turn them on and your set!! \t17/07/2017 17:50\n" +
                        "44\tOp***a\t5\tworks get use in my garage on second computer\t20/07/2017 12:30\n" +
                        "44\tHu***w\t5\tWorks as expected, good basic keyboard.\t27/07/2017 14:10\n" +
                        "44\tny***e\t5\tWorks great. Batteries came with it and they’re still working good. We use our laptop almost everyday for homework and stuff so I’m glad I bought this .\t31/07/2017 10:30\n" +
                        "44\ter***s\t5\tMy laptop screen stopped working, so to save money I\'ve been using an old monitor so I can still use it. I bought this so that I don\'t have to have my bulky laptop on my desk at all times, to give me more room. It works really well and is super easy to use. I\'m so glad that it\'s a combo of keyboard and mouse, and that they work with the same usb. My laptop only has two ports, so it would\'ve been a struggle if I needed to write papers on my storage usb when I would need to use those two ports for a keyboard and mouse. Complete life saver!!!\t02/08/2017 12:40\n" +
                        "44\tru***t\t5\tOut standing key board\t06/08/2017 8:10\n" +
                        "44\tcr***y\t5\tEasy to connect!\t11/08/2017 11:10\n" +
                        "44\tho***n\t5\tThis mouse and keyboard combo is my first foray into wireless products for my computer. Set up was easy as you simply plug in the receiver and turn on your mouse and keyboard via switches on them. I\'ve enjoyed gaming in bed and being able to hook up my desktop to my tv for ease of use. The connection if fairly strong as long as there is nothing in-between the receiver and your keyboard/mouse. I did try putting the receiver behind my tower and that is when I had some problems, so if you are close, it shouldn\'t be a problem, but after about 10 feet or so, it gets spotty. All in all, I\'m very satisfied.\t13/08/2017 4:20\n" +
                        "44\tTu***d\t4\tWorks as it should and as expected.\t15/08/2017 14:31\n" +
                        "44\tse***o\t5\tLove this! Works great and no wires!\t24/08/2017 22:30\n" +
                        "44\tgi***t\t5\tLove the layout! A winner!\t28/08/2017 21:11\n" +
                        "44\tdi***e\t5\tIt\'s great to have wireless full size keyboard & mouse. I like the nano-receiver fits in the mouse where the battery is. I like that I only have to have one nano-receiver that can be put in to any USB port and control both the keyboard and mouse. I used to have a wireless keyboard with its own nano-receiver and a wireless mouse with its own nano-receiver. I got tired of having both of them which prompted me to search for this product. I recommend this product as it has worked great for me.\t30/08/2017 19:54\n" +
                        "44\tCt***s\t5\tGreat product and came quicker than expected.\t07/09/2017 18:30\n" +
                        "44\tni***s\t5\tQuality product as stated.\t10/09/2017 21:54\n" +
                        "44\tmo***f\t5\tSimple but good\t15/09/2017 21:52\n" +
                        "44\tyo***t\t5\tThis keyboard has a great feel, and I love the on/off switch. Good purchase.\t17/09/2017 23:10\n" +
                        "44\tnu***k\t5\tGreat and long range keyboard and mouse.\t21/09/2017 8:30\n" +
                        "44\tmo***l\t5\tVery Nice\t23/09/2017 19:24\n" +
                        "44\tFy***t\t5\tProduct as described and delivered on time\t24/09/2017 21:10\n" +
                        "44\tlo***a\t4\tThe only things I can say I did not like is that the keyboard keys are loud when you push them. I like a softer feel. Also there is no light to let you know if num lock is on so you have to guess. Other than that I would say this combo is pretty good.\t25/09/2017 8:39\n" +
                        "44\tja***i\t5\tMom loves it.\t26/09/2017 2:10\n" +
                        "52\tan***e\t5\tWorks well\t08/07/2017 10:50\n" +
                        "52\tBu***q\t5\tAwesome, easy to use. love it.\t14/07/2017 18:59\n" +
                        "52\tki***k\t5\tFast delivery good product. It does not have Kodi Pre-Installed which is a plus for me it does have TVMC which is very important for me. I recommend this SMALLRT X2 Android tv Box!\t17/07/2017 7:40\n" +
                        "52\tya***j\t5\t\"This android box is small enough to take with you while travelling.<br/>" +
                        "There are a lot of good apps already installed too. This is a great box for how<br/>" +
                        "inexpensive it is.\"\t26/07/2017 14:40\n" +
                        "52\tDa***d\t5\t\\\"Not only for TVMC, it\'s very good, but with the TV Store(which is developed by the manufacturer)with countless other apps the choice is crazy and excellent.<br/>" +
                        "I do not have any problems, I found this model is very effective. It seems that I might come back to buy more.\t01/08/2017 13:32\n" +
                        "52\tvr***e\t5\tI have tried multiple streaming TV boxes. This one is by far the best I have come across. I love the layout of the software. Comes already with Android 6.0. loaded with Kodi. Plenty of ram for streaming live sports, live TV through sling TV, great with nextflix and YouTube. Hulu is also good. I am available if one might have any question about this device.\t04/08/2017 14:11\n" +
                        "52\tBr***h\t4\tThis is an awesome little machine! You can turn any screen into a smart tv of the highest quality for a very reasonable price! It comes preinstalled with Kodi and is easy to use as soon as you plug it in. I recommend using an ethernet cable for internet to avoid dropped signals from wifi, that\'s specifically why I got this after my fire stick. The touchpad keyboard comes in handy when you need it for typing and using a mouse. I haven\'t used it for games but it looks like it\'s designed for games as well. The only reason I gave this four stars is because I can\'t get Netflix at all, it\'s not on the device and it\'s not in the app store. From the looks of it, I\'ll have to reformat the tv box and put my essentials on there. Head\'s up, you want to be a little tech savvy if you want to start customizing the tv box, which it totally is customizable!\t10/08/2017 11:48\n" +
                        "52\toa***s\t5\tThe included addons were a little hit and miss, but I removed them and replaced them with the ones I want and it runs really well.It can be a bit laggy at times, but I would put it to my WiFi. I hooked it up with a cable from my router and it runs great!Need to clear the cache regularly so it stays smooth.For the price I couldn\'t ask for more.If you want a good android box, this is a great buy. \t14/08/2017 5:04\n" +
                        "52\tse***r\t5\tThis box is truly high quality with 2gb of ram and 16gb of storage. Also compatible with 5ghz WiFi and had Bluetooth 4.0. This box runs like a dream through all the different applications like browsing the Internet, Kodi, Netflix, and Hulu. The ability to download different apps and run them with ease. With the tech industry always advancing this box makes me happy that it can keep up with the speed. \t18/08/2017 9:30\n" +
                        "52\tdr***o\t5\tGreat device, fast, nicely specified and works without trouble. This box is great I have recommended it to a lot of people. It was well worth the price and allows you to watch a series movies pretty much ever! The box can provide many different options for you. You can use it as a smart phone. I think this is a brilliant little TV BOX!\t21/08/2017 6:40\n" +
                        "52\tha***t\t4\tI haven\'t used it for games but it looks like it\'s designed for games as well. The only reason I gave this four stars is because I can\'t get Netflix at all, it\'s not on the device and it\'s not in the app store. From the looks of it, I\'ll have to reformat the tv box and put my essentials on there. Head\'s up, you want to be a little tech savvy if you want to start customizing the tv box, which it totally is customizable!\t28/08/2017 18:12\n" +
                        "52\tav***v\t5\tMy older Box could not update to the newest version and I have spent months and months trying to sort it out. It would not steam most things and even when it did, it would stop playing half way through and constantly paused. I could never watch anything HD as they would be totally out of sync and very, very blocky. I even changed internet providers thinking the connection was the issue.Bought this and so glad I did as everything plays perfectly. Fantastic, plus the keyboard remote makes it so much easier!\t01/09/2017 13:50\n" +
                        "52\tNe***r\t5\tIt\'s smaller than I thought, but love it! Took me a while to figure it out, no instructions came with it. I\'ve downloaded at least a dozen apps since my purchase, and my daughter has downloaded many of her games. Love bring able to add what I want to it!\t07/09/2017 11:54\n" +
                        "51\tla***x\t5\tReally like the keyboard that came with it. Very easy to use, have it hard wired so don\'t really use the WiFi feature.\t11/09/2017 23:15\n" +
                        "51\tti***m\t5\this box is small but mighty in power. It does everything you need and it does it very well. It has twice the memory of the boxes and it is a real performer. It has everything you need already on it And comes with free wireless keyboard for easy use.. Enjoy! I know I do!\t14/09/2017 16:38\n" +
                        "51\tRe***d\t5\tlike\t19/09/2017 12:30\n" +
                        "51\tpo***p\t5\tQuality product \t21/09/2017 15:10\n" +
                        "51\tЮ***Ч\t5\tподключил коробку к телевизору, обновленному до криптона 17.3, и за 5 минут сделал так много выбора.\t23/09/2017 8:30\n" +
                        "51\tも***へ\t5\t私は遊んで、そしてアンドロイドと一緒に来るすべての改革反対、それは権利とやりやすく、 これまでに投げたすべてのビデオファイルを再生し、netflixの4Kは素晴らしいです。<br/>" +
                        "<br/>" +
                        "ラムの3ギガは素早く素早く動きますが、現在の標準でなければなりません。リモコンは大丈夫ですが、私はバックライト付のボタン付きエアーマウスに昇進しますが、<br/>" +
                        "<br/>" +
                        "全体的にこれまでのものはうまく動作しており、これは私が購入した2番目のものです。その小さなtvボックスです。\t24/09/2017 9:10\n" +
                        "51\tni***x\t5\tTV box is also the best and easy to work.\t26/09/2017 4:20\n" +
                        "35\tつ***や\t5\t私はこの製品を1週間以上働いていて、私のニーズに合わせてすべてのボックスをチェックしています。 高品質のメディアマシンとして、私たちのインターネット速度が最高ではないキプロスで間違いなく動作します。\t05/07/2017 21:41\n" +
                        "35\tㅘ***ㅅ\t5\t이것은 로컬 영역 네트워크에 직접 연결하여 조금 더 빨리 작동하고 나는 라디오에서 꽤 버퍼링을 가져옵니다. 나는 다른 사람들로부터 당신이 더 기억을 가진 상자를 얻으면 더 매끄럽게 달리는라고 읽는다. 나는 그것이 업그레이드 가능하다고 생각하지 않으며 3GB의 DDR3가 설치되어 + 32 GB ROM이 포함되어 있습니다. 꽤 작습니다.\t09/07/2017 16:31\n" +
                        "35\tmu***y\t5\tBox is really snappy. Loads stuff fast. Very responsive. Stock launcher is lame, but easily fixed with launcher of ur choice. Really pretty led around box looks pretty nice in the dark. Option exists to change color as well. Awesome.\t13/07/2017 13:37\n" +
                        "35\top***s\t5\tGreat as describes\t15/07/2017 7:55\n" +
                        "35\tce***c\t5\tCracking gadget does everything you could wish for and more. Bargin!! Very fast and easy setup.\t18/07/2017 11:14\n" +
                        "35\tfi***o\t5\tExcellent little box. Nice and fast and easy to use.i purchased the wireless keyboard too and makes using the box a lot easier.very happy with it and at a very good price.\t21/07/2017 20:40\n" +
                        "35\tyi***d\t4\tDon\'t have a problem. Beautiful! Highly recommend. We love it!\t24/07/2017 12:10\n" +
                        "35\tυΤ***λ\t5\tέξοχος!!!!!!!!\t29/07/2017 17:50\n" +
                        "35\tar***g\t5\tWorks faster that other ones I have purchased.\t01/08/2017 14:30\n" +
                        "35\tyu***d\t5\tGREAT BOX,NO PROBLEMS,\t04/08/2017 4:01\n" +
                        "35\tog***i\t5\tSuperb piece of equipment! Really easy to install and use! no fuss, just plug it in and connect to your wi fi and away you go! Best thing ive bought in ages!\t07/08/2017 11:41\n" +
                        "35\tju***e\t5\tI really like it. Better than FIRE TV.\t11/08/2017 21:50\n" +
                        "35\tme***s\t5\tVery pleased with this box! Performs flawlessly every time.Streams perfectly with now glitching what so ever. Super simple to operate, anyone can use it. I will recommend purchasing a Rii mini keyboard as I did with it. the remote that comes with it is very limiting for what you can do with this beast! \t15/08/2017 11:11\n" +
                        "35\thy***o\t5\tThe box is small so that I can convenience to move and put it in every position. The box has a nice overlay home page that gives it the Media Center, make it simple to navigate. The system firmware supports to online-upgrade. As for the software in it, most can upgrade automatically. It help me save much time.\t18/08/2017 9:04\n" +
                        "35\tGa***k\t5\tLove this Box! 4K 60fps video is smooth as silk with the H.265 video decoding and the Octa-Core CPU. The included software is well worth the price. KODI sets the value well above the selling price. \t21/08/2017 21:00\n" +
                        "35\tho***l\t5\tFantastic Android TV hardware. 2GB of RAM makes a massive difference compared to the majority that only have 1GB. I didn\'t like the launcher though so I installed TVLauncher, beautiful display now. \t25/08/2017 22:31\n" +
                        "35\tlo***s\t5\tHas not disappointed. It came with all the instructions needed to be able to set it up if you don’t know how to do. After a week of using this box, I was happy with the way it performed. Everything working great. The layout of the home screen is clean and intuitive, with appealing colors. There are only a handful of apps installed but they are the main one I need. You can download the apps you want easily from the market as well. It is easy to connect my television.\t28/08/2017 14:10\n" +
                        "35\tyi***a\t4\tThese are the best boxes. I\'ve bought a few of these for friends and family. Will probably invest in one for me. Seems to be much better processing speed.\t31/08/2017 8:00\n" +
                        "35\tmu***s\t5\tThis box is by far the best I have come across. It is rapid and stable. It’s compatible well with my TV. The setup isn’t complicating and I only spend 2-3 minutes. The keyboard is really cool for its backlight. It can help me use more convenience in dark. The remote control also works well. In a short word, the item is extremely awesome. It is worth with this price. And I was satisfied with customer service for I can get prompt reply when I have problems in using the box.\t01/09/2017 15:40\n" +
                        "35\tTu***t\t5\this box was everything I hoped for and more! Works great for me either on WiFi or Ethernet. Easily upgraded to the latest version of Kodi (17.1) I believe. I tried a different box with a quad core processor, this box is more than twice as fast!!! The remote is awesome as well...I highly recommend!!!\t05/09/2017 8:07\n" +
                        "35\tmo***r\t5\tThis is a great box for the price, set up was easy. Just let HDMI cable and power cord to connect,then power on the box.Then go into settings and connect wifi or plug the ethernet cord. I connect a 50MB Internet connection via LAN and watch 1080P movies smoothly.There are a wide variety of useful preloaded apps, such as Google Play Store, Youtube,Facebook,iCloud TV,etc.Once enter KD PLAYER,you can see many add-ons. Here I would like to recommend a great add-on called Ares Wizard. Open your web browser and type in the search bar \\\" How to Install Ares Wizard Kodi\\\", this search will give you various step by step guide on how to install the wizard on your box. The Ares Wizard allows you to install a large number of builds and chose the most popular builds out at the moment. The build you chose will continuously be updated so no dead links.\t09/09/2017 18:20\n" +
                        "35\tTa**p\t5\tWas very easy to setup and use. I already had a similar device and wanted one that was as close to that one as possible without the high price tag. I read all the reviews and liked that this came with the keyboard. This was a gift to my sister and she loves it. A really good value for the price.\t11/09/2017 23:33\n" +
                        "35\tke***y\t5\tHaving delt with various file formats and input devices for viewing video over the years, this little gem takes the prize. Small, inexpensive and solid. It takes a few minutes to navigate to where you want, but it simply works. Even used a 4TB portable drive with it....no issues. Great product for the price!\t15/09/2017 7:30\n" +
                        "35\tci***u\t5\tI love this Android box. Very easy to use and works with apps from google play store. No buffering and most videos play well including from Web browser. KODI also works well. The customer support is very good as I needed a new adapter for the power and they shipped it right away. I had faulty power adapter with original pack.\t20/09/2017 22:34\n" +
                        "35\tca***o\t5\tI received the unit in good condition. The T95ZPlus does everything I needed to do it streams very well. However the remote control that comes with it is not very good so I had to download a app on my phone to use with the T95Z. Also the customer service is wonderful I am completely satisfied .\t21/09/2017 11:22\n" +
                        "35\tyi***p\t5\tAwesome would recommend it to anyone and I\'m very pleased with it\t22/09/2017 14:13\n" +
                        "35\tNa***y\t5\tThe price is very attractive, compared to the Shield, but it is not worth it since it is pretty much useless. I am returning it and there are a couple other alternatives that I am going to try. Unfortunately, I can not recommend this to anyone. So much potential.\t24/09/2017 11:58\n" +
                        "35\tni***f\t5\tI really like this product. it works great and the picture is really good. It does pause occasionally, but I think that has something to do with the WiFi speed.\t25/09/2017 23:54\n" +
                        "58\tYO***S\t5\tThis is a very affordable waterproof case that can fit with almost all the smart phone. The camera quality underwater doesn\'t get effect much. N it\'s convenient for my husband n I since we uses diff type of phone \t27/09/2017 23:54\n" +
                        "58\tha***u\t5\tVery good product, big enough to fit my iPhone 6 ...\t28/09/2017 11:42\n" +
                        "58\tta***i\t5\tTested the case with running water and my phone store inside did not get wet. I am going to bring it with me for my sunshine and beach trip next month.\t28/09/2017 16:21\n" +
                        "57\toi***e\t5\tsuper cute, love the design,the quality is well\t27/09/2017 16:21\n" +
                        "57\tcc***f\t5\tmy daughter love it so much,it is very light so it is not bumpy to carry around. And USB rechargeable type is also useful. You can also combine with a mobile battery for charging on the go.\t28/09/2017 10:09\n" +
                        "56\twe***y\t5\tI use it by putting it in the rail of a toilet.It will be safe from now as it gets sweaty if you stay for 5 minutes in the summer.\t25/09/2017 15:21\n" +
                        "56\top***l\t5\tIt is easier to use than I thought. The design is cute, so when you go out, it is not uncomfortable to use instead of a fan.\t27/09/2017 23:54\n" +
                        "55\tse***r\t5\tcute, not very big\t27/09/2017 23:54\n" +
                        "66\tta***i\t5\tCompact inflator, yet it inflates fast!\t20/09/2017 22:34\n" +
                        "66\tze***y\t5\tI bought this air pump to replace the old one I got 3 yrs ago. This is almost half size of the old one. But surprisingly, it inflates tires much faster!\t21/09/2017 11:22\n" +
                        "66\tmi***y\t5\tThis inflator is pretty heavy, thought the size is compact. It gives me a solid feeling. Nothing to complain, five start!\t22/09/2017 14:13\n" +
                        "66\txo***x\t4\tThe built in gauge is handy, and the fact that the hose screws onto the valve stem is a real plus. The cord is nice and long, and allowed me to reach all 4 tires. One thing i can say is to be careful when disconnecting it, as the nozzle and hose do get quite hot. Not hot enough to burn yourself, at least not after 3 minutes of pumping.\t24/09/2017 11:58\n" +
                        "66\tli***y\t5\tgreat little tire pump. \t24/09/2017 15:16\n" +
                        "66\tpo***q\t5\tBefore the celestial just gas did not gas, and with this fast and good. It is easy to put in the car does not occupy the position\t25/09/2017 23:54\n" +
                        "66\tat***s\t5\tFive Stars\t26/09/2017 11:42\n" +
                        "66\tye***i\t5\tA must have for your auto. I have a tire that has a slow leak requiring me to add air about every 4-5 days. Trying to find a gas station that has air, then having to scrounge for $1.50 in quarters is a hassle. A storage bag is included. It\'s small so it doesn\'t take up a lot of space in your car. A great gift for the new driver.\t26/09/2017 16:21\n" +
                        "66\tta***i\t5\tIt does the job. Good for the price.\t27/09/2017 23:09\n" +
                        "65\tme***s\t5\tGreat little pump to keep in the vehicle for emergency\t16/09/2017 3:21\n" +
                        "65\thy***o\t4\tit\'s ok\t19/09/2017 13:12\n" +
                        "65\tGa***k\t5\ti did try on my bicycles, it would really surpise me that work very fast. \t20/09/2017 17:34\n" +
                        "65\tho***l\t5\tcomes with two adaptors to inflate diff. items. Its have the small area to place the adaptors and secure fit to make every thing so organized.\t23/09/2017 19:44\n" +
                        "65\tlo***s\t5\tThe device is pretty straightforward to use. \t23/09/2017 9:52\n" +
                        "64\tli***n\t5\tnot bad\t26/09/2017 11:46\n" +
                        "54\tvi***h\t5\tThis portable power bank car jump starter is built tough and rugged. It has enough current to be able to jump start a car, a truck or a lawn mower.\t27/09/2017 9:23\n" +
                        "53\tja***k\t5\tBest for what if situations\t05/09/2017 16:30\n" +
                        "53\tTo***h\t5\tIt\'s compact size allows the Jumpstarter to be easily concealed under a front seat\t07/09/2017 11:22\n" +
                        "53\tpA***L\t5\tThis is great!\t07/09/2017 14:13\n" +
                        "53\tST***N\t4\tIt\'s very light, not like those batteries are super heavy. \t10/09/2017 11:58\n" +
                        "53\tto***S\t5\tIt is good to have one just in case your car battery is dead\t11/09/2017 22:34\n" +
                        "53\tNI***O\t5\t portable and can be placed under the passenger seat\t14/09/2017 22:34\n" +
                        "53\tsa***s\t5\tEverything in the kit fit nicely in a great quality storage bag. Way to keep my car tidy, and ready for rainy days.\t18/09/2017 11:22\n" +
                        "53\tch***y\t4\tHi received the order today ,thanks\t19/09/2017 14:13\n" +
                        "53\thy***z\t5\tmy bf used to jump start his BMW L6, this little guy had no issue to jump start \t19/09/2017 11:58\n" +
                        "53\tcj***c\t5\tThis product is amazing! I first found out about it when my battery died at the mechanics shop and he pulled out this thing and jump started my suv. I was impressed.\t22/09/2017 15:16\n" +
                        "53\tha***c\t5\tHad to use it today. My cars brake light kept staying on even after you take the keys out. It killed the battery. I used this device and at first I thought it wasn\'t working.\t22/09/2017 23:54\n" +
                        "53\tno***z\t4\tnot bad\t24/09/2017 11:42\n" +
                        "53\tze***y\t5\tit\'s work\t24/09/2017 16:21\n" +
                        "53\tmi***y\t5\teasy to use, I didn\'t follow the guide\t26/09/2017 23:09\n" +
                        "49\txo***x\t5\tgood quality, so far works well\t02/09/2017 11:22\n" +
                        "49\tli***y\t5\tlook the design of this box\t03/09/2017 14:13\n" +
                        "49\tpo***q\t5\tmy frist tv box, works good\t07/09/2017 11:58\n" +
                        "49\tat***s\t5\ti like it！It came ready for use.\t09/09/2017 15:16\n" +
                        "49\tye***i\t5\tCan\'t beat the price! Excellent Box!\t09/09/2017 23:54\n" +
                        "49\tta***i\t5\t I had probably one of the best experience with Customer Service, they were very helpful and courteous, this is one thing that I look for when I purchase a product is the Customer Service! If you have ANY issues, they are there to help you! \t15/09/2017 11:42\n" +
                        "49\tme***s\t5\tCan\'t say anything negative on the box, it works great and I am very happy with my purchase!\t17/09/2017 16:21\n" +
                        "49\tDa***l\t5\tThis is the best thing going.\t20/09/2017 23:09\n";
        lines ="38\tli***k\t5\tProduct was exactly as described and shipped quickly.\t06/07/2017 8:30\t\n" +
                "38\tpl***o\t5\tWorks great once I figured it out. Still need to get time to fully take advantage of the device.\t10/08/2017 7:45\t\n" +
                "38\twr***l\t5\tworks great just what i wanted .great item for price\t15/09/2017 9:34\t\n" +
                "38\tqi***y\t5\tI have thoroughly enjoyed using this device for watching different types of entertainment on my TV\t09/09/2017 21:37\t\n" +
                "61\tgl***t\t5\tI picked up some more channels ones I got this unit up dated really love it\t23/07/2017 6:40\t\n" +
                "61\tが***る\t4\t\"安く購入し、満足しています。接続・使い方全て解りやすい！<br/>画像も綺麗で、動きも問題無しです。　\"\t01/08/2017 22:10\t\n" +
                "61\tGa***z\t4\t\"Ho acquistato questo apparecchio per curiosità. Debbo dire che sono contento dell\'acquisto, funziona perfettamente ed è semplice da usare, nonostante le istruzioni in inglese. <br/>Consigliato\"\t05/08/2017 14:50\t\n" +
                "61\tFr***k\t5\tExcellent box for the price. Easy setup.\t11/08/2017 13:10\t\n" +
                "61\tLe***h\t5\tThis is a absolutely great box. The os is very clean and the box and much faster than many more expensive ones. \t14/08/2017 21:20\t\n" +
                "61\toc***y\t5\tPlugged in, setup WiFi and it has been doing its job ever since with no problems, would recommend to others, thanks.\t23/08/2017 6:50\t\n" +
                "61\tTo***y\t4\tI\'ve had a few Android TV boxes now. The main two that I use are the Leel box, since those were extremely popular when I first bought into them. They have been a solid benchmark going forward, and they work great. These can almost measure up.  but there is less RAM.\t05/09/2017 19:23\t\n" +
                "61\tLu***y\t5\tThis box is  very easy to set up. I downloaded YouTube, Netflix, and TV Plus App so far. It runs very fast and the picture is really really clear. I love it so far!\t09/09/2017 8:30\t\n" +
                "61\tYe***l\t5\tI really enjoyed watching movies and news on this box. I was able to watch tv shows and CNN news on the tv. I had cancel out my cable for my tv after having this. This is saving me a lot of money. It is very easy to setup. All you have to do is follow the instructions on the tv. I really love it.\t13/09/2017 20:10\t\n" +
                "43\tte***y\t4\tgreat product makes my tv watching better than ever. I was concerned it would be complicated to use but even with a far router and wifi it gets the job done\t17/07/2017 9:17\t\n" +
                "43\tqi***n\t5\tThis box is great!! Was super easy to hook up to my tv and made it so that I can watch all my shows and movies I can\'t ,get on my cable box.   SO happy I bought this box!!\t21/07/2017 23:07\t\n" +
                "43\tLe***t\t5\tThis is a fab piece of kit works really well and does everything I want and more. I\'m not tech savvy but even I managed to set this up and use worth every penny\t29/07/2017 7:02\t\n" +
                "43\twr***p\t5\tThis is my first TV box. i hesitated a while and finally decided to buy this one because of its high properties. This is a good deal, especially after i have been using it 5 days. So far everything is good. I think it deserves a good recommendation.\t02/08/2017 9:15\t\n" +
                "43\tde***y\t4\tI got the TV Box in days. I am satisfied with the design of the package and the product. Very portable. It also has a good connection with my TV. I would recommend this product.\t07/08/2017 20:19\t\n" +
                "43\tab***y\t5\tThis Android TV box is amazing!  I have this TV box. The set up is very easy, just connect the cable to TV and power line, connect to wifi,I can just relax and enjoy the favorite TV shows. Besides youtube, I also can download apps. The TV box responds very fast and works well. \t13/09/2017 23:09\t\n" +
                "42\tru***k\t5\tGreat tv box to own! Very easy to set up and tons of apps available to stream videos. Once you set it up with tv, you would get exactly same kinda feeling like you watch multimedia through cable or satellite tv, but instead of paying fee for the services. Worth to try, glad to own!\t18/08/2017 19:29\t\n" +
                "42\tう***ん\t5\tgood, will order another one for my parents\t25/08/2017 10:40\t\n" +
                "42\tCh***r\t5\tThis is a great box it\'s very easy to install it took at the most 5 minutes it\'s very sturdy .  it\'s very easy to download additional ones. I highly recommend this TV box!\t02/09/2017 11:20\t\n" +
                "42\tir***y\t5\tI had an issue with the box not streaming, I contacted support and they gave me instructions to correct the issue. Box works great customer service was excellent.\t13/09/2017 18:46\t\n" +
                "70\tDa***l\t5\tThis is the first android tv box I\'ve purchased and I\'m fairly happy with it. No performance issues and it the video quality is great when you have a proper connection.\t05/07/2017 21:41\t\n" +
                "70\tAn***s\t5\tIt\'s good! It was smooth, easy to get along with, operate and actually use.\t09/07/2017 16:31\t\n" +
                "70\tGi***e\t5\taffordable streaming box \t13/07/2017 13:37\t\n" +
                "70\tTT***r\t5\tEasily hooks up and works flawlessly so far\t15/07/2017 7:55\t\n" +
                "70\tzd***m\t5\tI must say that this is a real good set top box. It\'s basic function is to turn your old LCD or LED Tv into a Smart TV.\t18/07/2017 11:14\t\n" +
                "70\tst***s\t5\tAwesome. Setup is a breeze. Quick and easy. Well worth the money.\t21/07/2017 20:40\t\n" +
                "70\t Ru***m\t5\tvery cheap, good box for beginners\t24/07/2017 12:10\t\n" +
                "70\t CO***S\t3\tit is bigger than I thought, not suits to the gap between my tv and the wall\t29/07/2017 17:50\t\n" +
                "70\tAna***\t5\tGreat little box,five star\t01/08/2017 14:30\t\n" +
                "70\t Iv***l\t5\t5 stars\t04/08/2017 4:01\t\n" +
                "70\tdz***a\t4\tit will be more great if the box have 2gb+8gb, i like the design of the box very much\t07/08/2017 11:41\t\n" +
                "70\tев***о\t5\tThe price is very attractive, with code I even save 15%\t11/08/2017 21:50\t\n" +
                "70\tJen**o\t5\ta necessity if you have kids\t15/08/2017 11:11\t\n" +
                "70\tKay**e\t5\tAbsolutely happy product from Leelbox again! \t18/08/2017 9:04\t\n" +
                "70\tPai**i\t3\tshipping could be better\t21/08/2017 21:00\t\n" +
                "70\tAn**i\t5\tNo performance issues and it the video quality is great when you have a proper connection.\t25/08/2017 22:31\t\n" +
                "70\tJ.***o \t5\tI have already experimented with other downloaded links. They range from broken to excellent, meaning quick connect and 1080P. \t28/08/2017 14:10\t\n" +
                "70\tSe***a \t5\tthe picture was great, fine\t31/08/2017 8:00\t\n" +
                "70\tKe***s\t5\tI have watched many movies and surfed the internet so far with this box no problem!\t01/09/2017 15:40\t\n" +
                "70\tBr***n\t5\tPress HELP button to download apps you want. There is a TVMC, it’s powerful, you can watch Youtube, stream Netflix, listen to Spotify or even browse the internet from your TV. \t05/09/2017 8:07\t\n" +
                "70\tKe***s\t4\twith wireless keyboard you will have better control\t09/09/2017 18:20\t\n" +
                "70\tRo***n\t5\tIts Android and its super easy to use\t11/09/2017 23:33\t\n" +
                "70\tNo***m\t5\torder 3 boxes, for myself and my sisters, not try it yet\t15/09/2017 7:30\t\n" +
                "70\tmo***f\t4\tgood\t16/09/2017 17:32\t\n" +
                "70\tyo***t\t5\tFive star!!\t25/09/2017 22:34\t\n" +
                "70\tnu***k\t5\tHave been watching some movies and TV shows with only a tiny bit of an issue with buffering and need to clean up memory, but most of the time, it works fine.\t27/09/2017 11:22\t\n" +
                "70\tmo***l\t5\tworth the money\t28/09/2017 14:13\t\n" +
                "70\tFy***t\t4\tthe memory is not big, you should clean up memory often\t28/09/2017 11:58\t\n" +
                "70\tLu***y\t5\tgood box,easy to use even if you are not tech savvy\t01/10/2017 23:54\t\n" +
                "70\tYe***l\t5\tgood\t02/10/2017 23:54\t\n" +
                "70\tdr***r\t5\tpretty easy to figure out how to use it. I went to settings, wifi, and then entered my internet details to get this box on the internet. It went through an update on a few of the programs pretty easily and I was up and running in no time at all.\t04/10/2017 23:54\t\n" +
                "70\tte***y\t5\tnice\t05/10/2017 23:54\t\n" +
                "71\tDa***l\t5\tFunky looking box\t17/09/2017 22:11\t\n" +
                "71\tAn***s\t5\toh its a great box fast reliable i love\t16/09/2017 16:56\t\n" +
                "71\tGi***e\t5\tIt is easy to navigate. Just plug and play, don’t need any other operate. The interface is designed simple for ease using. Supporting a variety of video and applications. It has so many apps and functions on it. I got this item for a few weeks and it still works well without any noise or problem.\t29/09/2017 20:23\t\n" +
                "71\tTT***r\t5\tbig memory, runs very well\t02/10/2017 15:44\t\n" +
                "74\tSe***a \t4\tthe box will work as fast as your Wifi connection will allow\t14/09/2017 22:34\t\n" +
                "74\tKe***s\t5\tLots of memory and fast\t18/09/2017 11:22\t\n" +
                "74\tBr***n\t5\tThe keypad with box makes using the systems so much easier than the remote\t19/09/2017 14:13\t\n" +
                "74\tKe***s\t5\tLove it, easy to set up. Well happy with my purchase \t01/10/2017 11:58\t\n" +
                "31\tKay**e\t5\tworks. Bought as a replacement for one I use ...\t30/06/2017 8:30\t\n" +
                "31\tPai**i\t4\tGood product and Kodi works well on it. The only reason I gave it 4 stars and not 5 is that the remote,I don\'t like the design.Other than that I am happy with it and am glad I purchased it.\t30/07/2017 21:40\t\n" +
                "31\tAn**i\t5\tQ2 Pro is easy to use and very entertaining. I recommend getting the keypad/qwerty remote additional for easy use.\t05/09/2017 16:31\t\n" +
                "31\tJ.***o \t5\tI did my research and found this product which has great reviews and decided to give it a try. I was able to set it up and started watching moves pretty quickly. There are a lot of youtube video that shows how to set up the device and it was pretty easy to follow the steps. So far so good but I only give 4.5 stars for didn\'t get discount.\t05/09/2017 23:37\t\n" +
                "31\tSe***a \t5\tThis box works great with no lag. I did upgrade from kodi 16 to kodi 17, So I could down load the build I wanted the build that came on it had no sports.\t10/09/2017 7:55\t\n" +
                "31\tKe***s\t5\tThis box is great and it\'s even better for the price, it has all the top qualities you would want when looking for a tv box, I\'ve personally owned around 3 to 4 tv boxes and this stands up to any of them.\t13/09/2017 21:24\t\n" +
                "62\tBr***n\t4\tnot bad.\t15/09/2017 8:41\t\n" +
                "62\tKe***s\t5\tThe device already loaded with many great app out of the box, so I didn\'t need to download by myself, it\'s cool, this is my first box, I am learning to use it.\t16/09/2017 3:21\t\n" +
                "62\tRo***n\t5\teverything is fine, but with a keyboard it will be more easier to handle\t19/09/2017 13:12\t\n" +
                "62\tNo***m\t5\tBox works as described. I haven\'t tested any HD content as of yet. \t20/09/2017 17:34\t\n" +
                "62\tCr***L\t5\tThe product that they sent got some minor problems but the customer service responded so fast to make up for it.5 stars for the product,6 stars for the customer service.\t23/09/2017 19:44\t\n" +
                "62\tlm***q\t5\t The TV box itself is very small, small enough to bring around when go on a trip. It packs with all the inputs and outputs I need, hdmi,couple usbs and micro sd slot. The device already loaded with many great app out of the box. So far it supported all the apps that I downloaded from google store and from third party apk website. The streaming is smooth and picture quality is amazing. The Bluetooth feature was great. I was able paired my headphone easily and sound is on sync. Highly reccomended.\t23/09/2017 9:52\t\n" +
                "32\tst***s\t5\tso mini, so cute\t23/07/2017 16:31\t\n" +
                "32\t Ru***m\t5\t2/8gb, not bad\t16/08/2017 23:37\t\n" +
                "32\t CO***S\t4\tEverything played back smooth and getting around in it was fairly quick. I recommend getting a mini keyboard to use it, it\'s easier when it comes to typing and most have a little mousepad built into it which also makes it easier to get around the OS. \t24/08/2017 7:55\t\n" +
                "32\t Iv***l\t5\tThe 2Gb memory is really cool, it boost your online performance and reduces buffering. It works great on both Ethernet and Wi-Fi, I just wish that the Wi-Fi was 5Ghz. You\'ll love this Leelbox mini LoL\t20/09/2017 12:11\t\n" +
                "32\tdz***a\t5\t I stream youtube a lot with it and so far it works great. Definitely worth the price.\t22/09/2017 17:20\t\n" +
                "32\tев***о\t5\tThanks to 2GB Ram, the system is super fast and smooth. Compared to my old boxes, it also has Bluetooth and 5G wireless network.\t22/09/2017 19:41\t\n" +
                "32\tJen**o\t5\tHoly crap this box is amazing\t25/09/2017 15:22\t\n" +
                "53\tNI***O\t5\t portable and can be placed under the passenger seat\t14/09/2017 22:34\t\n" +
                "53\tsa***s\t5\tEverything in the kit fit nicely in a great quality storage bag. Way to keep my car tidy, and ready for rainy days.\t18/09/2017 11:22\t\n" +
                "53\tch***y\t4\tHi received the order today ,thanks\t19/09/2017 14:13\t\n" +
                "53\thy***z\t5\tmy bf used to jump start his BMW L6, this little guy had no issue to jump start \t19/09/2017 11:58\t\n" +
                "53\tcj***c\t5\tThis product is amazing! I first found out about it when my battery died at the mechanics shop and he pulled out this thing and jump started my suv. I was impressed.\t22/09/2017 15:16\t\n" +
                "53\tha***c\t5\tHad to use it today. My cars brake light kept staying on even after you take the keys out. It killed the battery. I used this device and at first I thought it wasn\'t working.\t22/09/2017 23:54\t\n" +
                "53\tze***y\t5\tit\'s work\t24/09/2017 16:21\t\n" +
                "53\tmi***y\t5\teasy to use, I didn\'t follow the guide\t26/09/2017 23:09\t\n" +
                "33\tEr***t\t5\tMuch faster processor and menu speeds. Didn\'t use the instructions that come with it, just set it up the same way as every other android box and was up and running in minutes.\t15/07/2017 21:40\t\n" +
                "33\tMa***n\t5\tAwesome. \t30/07/2017 16:31\t\n" +
                "33\tAl***o\t5\tDid everything It expected it to do.\t01/08/2017 23:37\t\n" +
                "33\tAl***o\t5\tIncredible Android TV box with very little to do to get it setup and start enjoying your favorites movies. I was surprised to see that the included HDMI cables are high end and not cheap cables like that are included in the other boxes that I have purchased. \t03/08/2017 7:55\t\n" +
                "34\tTa***r\t5\tDual wifi is really cool. I am using this in my upstairs guest bedroom and my router is located on the first floor. It is showing full signal and has no lag when playing 1080p movies. \t13/09/2017 21:24\t\n" +
                "34\tma***2\t5\talthough it is a little expensive than others, it worth the price, very good experience so far\t20/09/2017 8:41\t\n" +
                "34\tpp***u\t5\tvery good quality, better than I thought\t08/10/2017 14:41\t\n" +
                "42\tol***p\t5\tArrived ahead of schedule, ready to use, and we love it!\t14/09/2017 22:34\t\n" +
                "42\tuk***j\t5\tThis product performed as advertised.Iwould recamend it in a heartbeat\t18/09/2017 11:22\t\n" +
                "42\tyo***h\t5\tMy 3 year old box finally got too old for the old kodi. This one was amazing out of the box fully loaded! No issues.\t19/09/2017 14:13\t\n" +
                "42\tSP***Y\t5\tDelivered on time as advertised. Included mini keyboard and remote for box.\t01/10/2017 11:58\t\n" +
                "42\tRE***E\t4\tI like it,the Movies, TV Shows and Music do which is what I wanted it for.\t07/10/2017 15:16\t\n" +
                "43\tlm***q\t5\tFive Stars\t02/09/2017 12:54\t\n" +
                "43\tRE***V\t5\tso far everything runs well\t09/09/2017 18:26\t\n" +
                "43\tPai**i\t5\tExcellent\t17/09/2017 22:11\t\n" +
                "43\tbr***E\t5\tThis is my 2nd purchase from this vender. I received just what I ordered.\t16/09/2017 16:56\t\n" +
                "43\tII***i\t4\tIts not bad, first time buying one these boxes. I was expecting faster speeds but after doing some research, speeds depend on the source on the other side too. But I was impressed with the amount of content available. User interface was a little bit getting used to, but after a few days you get the hang of it.\t29/09/2017 20:23\t\n" +
                "43\tmo***l\t5\tcustomer service respon so fast\t02/10/2017 15:44\t\n" +
                "43\tru***k\t5\tVery fast delivery, great product\t08/10/2017 21:58\t\n" +
                "59\tMs.**a\t5\tGiven as gifts. easy to use and everything they wanted in a tv box.\t02/09/2017 22:11\t\n" +
                "59\tMa***k\t5\tSuper fast delivery . exactly what is advertised . Easy to just plug n play . No issues with this and very happy with it.\t08/09/2017 16:56\t\n" +
                "59\tDa***l\t5\tLove it thank you\t23/09/2017 23:23\t\n" +
                "59\tAn***s\t4\tSometimes it is difficult finding the right copy but all in all I am enjoying it!\t28/09/2017 1:44\t\n" +
                "59\tGi***e\t5\tgreat product, working very good\t03/10/2017 11:58\t\n" +
                "60\tdz***a\t5\tTruly awesome switch\t16/09/2017 9:17\t\n" +
                "60\tев***о\t5\tWork great!\t19/09/2017 22:34\t\n" +
                "60\tJen**o\t5\tSuperior product, at unbeatable price.\t22/09/2017 11:22\t\n" +
                "60\tNI***O\t5\tGreat reliable, easy to set up wifi enabled plug\t24/09/2017 14:13\t\n" +
                "60\tsa***s\t5\tL always did good quality built items. Works exactly as describe\t29/09/2017 16:51\t\n" +
                "60\tch***y\t5\tGreat product. Great seller. Good deal.I order 3 in one time\t01/10/2017 21:16\t\n" +
                "60\thy***z\t5\tI had installed these switches in 3 rooms in our home. Whenever we go away, I can turn the lights on in the home one by one, cool\t05/10/2017 12:16\t\n" +
                "63\tpp***u\t5\tthis little thing works fine, but not as great as I THOUGHT\t19/09/2017 14:13\t\n" +
                "63\tol***p\t5\tsurprisingly good image quality\t23/09/2017 16:51\t\n" +
                "63\tuk***j\t4\tProduct is excellent but like all of these types of antenna it’s best to change positions a few times after scanning to find out where it works best for you. \t05/10/2017 21:16\t\n" +
                "63\tyo***h\t5\tExcellent value for the money\t07/10/2017 22:16\t\n" +
                "41\tMa***n\t5\tAntenne légère qui capte bien les canaux HD disponibles dans votre région (c\'est normal de devoir ajuster au début pour trouver le meilleur endroit possible dans la maison).\t13/08/2017 16:51\t\n" +
                "41\tAl***o\t5\tAmazing saved me a monthly fee for cable tv. I live in the basement and still got 4 HDTV channels free.\t01/09/2017 21:16\t\n" +
                "41\tAl***o\t5\tReplaced my rabbit ear antenna with this. Wish I did it years ago. No more moving around the antennas to find a clear picture. This instantly worked when connected to tv !\t03/09/2017 19:11\t\n" +
                "41\tTa***r\t4\tDon\'t expect miracle but it was better than a bunch other ones i used before\t08/09/2017 16:56\t\n" +
                "41\tma***2\t5\tAmazing saved me a monthly fee for cable tv. I live in the basement and still got 4 HDTV channels free.\t23/09/2017 23:23\t\n" +
                "41\tpp***u\t5\tI admit I was skeptical, but this item far exceeded my expectations. I get 17 channels!\t28/09/2017 17:44\t\n" +
                "41\tJ.***o \t5\tGreat replacement for my previous antenna I bought in a condo. You need to test it out and find the sweet spot in your house.\t03/10/2017 22:58\t\n" +
                "54\tvi***h\t5\tThis portable power bank car jump starter is built tough and rugged. It has enough current to be able to jump start a car, a truck or a lawn mower.\t27/09/2017 9:23\t\n" +
                "54\tDL***N\t5\tgood\t29/09/2017 21:36\t\n" +
                "64\tli***n\t5\tnot bad\t26/09/2017 11:46\t\n" +
                "64\tlm***q\t5\tThe right protection to always have in your car. \t23/09/2017 17:23\t\n" +
                "64\tRE***V\t5\tgreat product & service\t28/09/2017 23:44\t\n" +
                "64\tPai**i\t5\tAwesome and portable\t03/10/2017 9:58\t\n" +
                "65\thy***o\t4\tit\'s ok\t19/09/2017 13:12\t\n" +
                "65\tGa***k\t5\ti did try on my bicycles, it would really surpise me that work very fast. \t20/09/2017 17:34\t\n" +
                "65\tho***l\t5\tcomes with two adaptors to inflate diff. items. Its have the small area to place the adaptors and secure fit to make every thing so organized.\t23/09/2017 19:44\t\n" +
                "65\tlo***s\t5\tThe device is pretty straightforward to use. \t23/09/2017 9:52\t\n" +
                "67\tев***о\t5\tFun Fidget Cube\t16/09/2017 9:17\t\n" +
                "67\tJen**o\t5\tI have no idea how this thing moves the way it does, but it is so cool!\t19/09/2017 22:34\t\n" +
                "67\tNI***O\t5\tBest mind game\t22/09/2017 11:22\t\n" +
                "67\tsa***s\t5\tGreat toy! The kids play with it all the time tying to get it and no I am no help :)\t24/09/2017 14:13\t\n" +
                "67\tch***y\t5\tyou can keep folding and folding and it\'s like a cross between the rubixs cube and fidget spinner.. so awesome.. the kids love it\t29/09/2017 16:51\t\n" +
                "67\thy***z\t5\tCube and star and two cubes and two stars and a star in the cube\t01/10/2017 21:16\t\n" +
                "30\tDa***l\t5\tsupports 4k tv, it\'s cool\t22/08/2017 16:58\t\n" +
                "30\tAn***s\t4\tno buffers, it will be great if the memory could b 2+8\t05/09/2017 13:24\t\n" +
                "30\tGi***e\t5\tFive Stars\t12/09/2017 17:55\t\n" +
                "30\tTT***r\t5\tIt has been pretty great so far although the memory is super low. Would recommend to anyone\t18/09/2017 23:16\t\n" +
                "30\tzd***m\t5\tA very nice product for the price\t24/09/2017 19:45\t\n" +
                "36\tga***t\t5\tOne thing that stands out is that the remote for this is very good and the box response sharply to the button commands. As you can see from the pictures I included the box is very small so it does not stick out. The blue light is not too bright either. Even though the CPU is not a high end one it does well with loading video\'s and apps. It comes loaded with its own version of Kodi 17.3 but I went ahead and updated it to 17.4. Out of the box it has a bunch of repositories included so if your not savvy with these boxes there is no worry, you can click and go! One feature I didn\'t realize was missing when I purchased was Bluetooth. Not a big deal for me as I had a specific use for this.\t31/08/2017 11:15\t\n" +
                "36\twe***n\t5\tI installed the Plex on this box and mounted it back of my Samsung tv, and connected the Plex to my Plex server, and I can watch everything I can in 4K resolution. and I don\'t have any cable dangling around as everything in on back of the TV. I would recommend this system to anyone who need it.\t08/09/2017 18:35\t\n" +
                "36\tSh***g\t5\tAwesome Android TV Box，Did everything it expected to do.\t10/09/2017 21:03\t\n" +
                "36\tsu***s\t5\tSame day received, same day hooked up. Instantly I watched what ever I wanted,2 movies and many sport channels.All in all a very good product at a reasonable price that will be used a lot. Going to order another one for my bedroom!\t19/09/2017 9:31\t\n" +
                "39\tal***w\t5\tThis thing is simply amazing.Very easy to install,it shown beautiful hd pictures and very fast when using,almost no buffering and freezing. Working great,I loved it.will share it with my friends.\t29/07/2017 8:40\t\n" +
                "39\tev***n\t5\tOutstanding Android TV box , highly recommend it!<br/>I love this android box! Because of I use it for watching TV paly games download apps......<br/>the r1 box support connect 64tb hard drive and my air mouse the same time,so that i can share my own movies and photos with my family and friends.<br/>it is easy to take the TV box into any of my rooms with a TV and just need connect it to the cables and wifi.super portable!\t11/08/2017 11:35\n" +
                "39\tWi***n\t5\tAmazing r1 player,Browser and youtube work without problems. I took the box connect with my air mouse so the remote control it\'s very convenient to work in the browser. Great! I am very happy with my box.\t03/09/2017 14:06\t\n" +
                "39\tuu***s\t5\tEnjoying it. It\'s more better than Amazon fire stick which I have used. no problem to setting up and you can watch play anything...I think it\'s worth. A+++\t14/09/2017 18:54\t\n" +
                "39\tpt***p\t5\t\"I would recommend this box to my friends and family. really a great Android product.<br/>I have tried a lot of different boxes and this one is the most user friendly one I have found. It\'s amazing mini size and easy to plug for use. I bought a second unit for my living room TV.\"\t19/09/2017 4:20\t\n" +
                "39\tmi***e\t5\tWhen my old WDTV box was getting a bit long in the tooth, I looked round for a suitable replacement. This tv box for me as it\'s future-proofed for 4K and can run apps such as YouTube, BBC iPlayer, Kodi and VLC. So that covers most of my viewing methods be they streaming . Pretty easy to set up and use, I\'m very happy with it.\t21/09/2017 13:54\t\n" +
                "39\tvo***l\t5\t\"WOO!!!!!This tv box is really convenient, can watch stuff that the phone didn\'t even update yet , it\'s really convenient<br/>This is my first Kodi box and I definitely recommend it. The instruction book is a little sparse but the box was very easy to set up and works well. The picture quality is excellent.\"\t23/09/2017 9:28\t\n" +
                "40\tMs.**a\t5\tGreat Quality Streaming Box\t19/09/2017 22:34\t\n" +
                "40\tMa***k\t5\tworth for the money!\t22/09/2017 11:22\t\n" +
                "40\tDa***l\t5\tWas a good choice will order again.\t24/09/2017 14:13\t\n" +
                "40\tAn***s\t4\tSeller contacted me and after trying to determine what was wrong, they are sending a replacement. They were very fast and I\'m looking forward to seeing how the new one performs and hopefully can upgrade my rating to 5 stars.\t29/09/2017 16:51\t\n" +
                "40\tGi***e\t5\tWorks flawlessly-Very Fast\t01/10/2017 21:16\t\n" +
                "40\tTT***r\t5\tthe box works as expected, came on time and now im enjoying watching my flix on my tv\t05/10/2017 12:16\t\n" +
                "40\tzd***m\t5\tI have not had any issues with this box so far\t19/09/2017 14:13\t\n" +
                "40\tst***s\t4\tgreat value and works better than its predecessors\t23/09/2017 16:51\t\n" +
                "40\t Ru***m\t5\tThis TV Box is great and easy to install and operate. I like it better than my roku stick.\t05/10/2017 21:16\t\n" +
                "40\t CO***S\t5\tNice box!\t07/10/2017 22:16\t\n" +
                "47\thi***p\t5\tExcellent! Got it in two days as promised, quick and easy to install.It was working within two minutes, fast and great picture. Way beyond my expectations! Would recommend anyone to purchase this, just wish it had more variety of colors to chose from, other than that it\'s excellent. Works well compared to others. \t03/10/2017 15:32\t\n" +
                "46\tma***c\t5\tConnects right away when I need it. So far the touch pad works great. I have not used every key but when I need to type something it works great. I would recommend this to anyone who needs a mouse for thier TV box.\t14/09/2017 7:40\t\n" +
                "46\tcu***d\t4\tOkay box, have to clear your cache often and force stop apps frequently. Okay for the price.\t30/09/2017 18:10\t\n" +
                "45\tAl***o\t5\tFirst time using it pre installed kodi great box\t03/09/2017 14:06\t\n" +
                "45\tAl***o\t5\thappy with the box\t19/09/2017 4:20\t\n" +
                "45\tTa***r\t5\tvery cute box\t23/09/2017 16:51\t\n" +
                "45\tma***o\t5\tGood device and excellent customer service\t11/10/2017 21:11";
        return lines;
    }
}

class Module {
    int star1 = 0;
    int star2 = 0;
    int star3 = 0;
    int star4 = 0;
    int star5 = 0;
    int starTotal = 0;
    double score = 0.00; // 总分
}