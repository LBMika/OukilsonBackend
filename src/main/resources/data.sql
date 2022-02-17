-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: oukilson
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `denied_list`
--

LOCK TABLES `denied_list` WRITE;
/*!40000 ALTER TABLE `denied_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `denied_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,'538e71c1-2205-4c63-8da0-91dffc90560a','Le super event',4,2,'2022-01-20 19:00:00',NULL,'2021-12-22 23:00:00','2022-01-19 11:00:00',3,6,'Un event de test',1,0),(2,'2020c1d2-7753-4541-8d34-f530f0bce6ac','Titre : Le super super event de la mort qui tue',1,3,'2022-03-12 11:00:00',NULL,'2021-12-22 23:00:00','2022-01-19 11:00:00',3,7,'Un event de la mort qui tue de test',2,0),(3,'4f449d3d-fc30-453a-8cd0-cb3fbcab4292','Le super event de la mort qui tue',1,3,'2022-01-08 19:00:00',NULL,'2021-12-22 23:00:00','2022-01-19 11:00:00',3,6,'Un event de test',3,0),(5,'43a11fbf-7412-463a-a038-a2b2d29c61e5','Le super event de la mort qui tue',1,3,'2022-01-28 11:00:00',NULL,'2021-12-22 23:00:00','2022-01-19 11:00:00',3,6,'Un event de test',4,0),(6,'e31252c9-fcf1-421c-ad0a-6b9f406f863f','C\'est le rush',1,3,'2022-01-20 19:00:00',NULL,'2021-12-22 23:00:00','2022-01-19 11:00:00',3,6,'Rush hour',6,1),(7,'8ac648c6-01ab-4b5c-9928-c1f14dc6245a','C\'est le rush 2',1,3,'2022-02-15 19:00:00',NULL,'2021-12-22 23:00:00','2022-01-19 11:00:00',3,6,'Rush hour 2',7,1),(12,'4f4d6815-255c-4f90-b35d-d10106d62424','Un event',4,6,'2022-02-02 18:00:00','2022-02-02 22:59:59','2021-12-25 11:00:00','2022-01-25 11:00:00',3,5,'Juste un event, bro !',22,0),(13,'00ae5a9e-c564-4d00-875e-e0d20b3fc4be','Un event',4,6,'2022-02-02 18:00:00','2022-02-02 22:59:59','2022-01-12 10:57:46','2022-01-25 11:00:00',3,5,'Juste un event, bro !',23,0),(14,'11e0e630-b285-4c84-a6e6-d7f286b9aa4a','Un event',4,6,'2022-02-02 18:00:00',NULL,'2022-01-12 13:52:09','2022-01-25 11:00:00',3,5,'Juste un event, bro !',24,0),(15,'c8e897cd-4490-463f-a0c6-73f6c0e6cae0','Un event',4,6,'2022-02-02 18:00:00','2022-02-02 22:59:59','2022-01-12 13:53:05','2022-01-25 11:00:00',3,5,'Juste un event, bro !',25,0),(16,'1a994bd3-987b-440c-a42c-45c37ed98443','Un event',4,6,'2022-02-02 18:00:00',NULL,'2022-01-12 13:55:14','2022-01-25 11:00:00',3,5,'Juste un event, bro !',26,0),(17,'4e6c0c57-a50b-4e58-bef8-6b7cfae3cfbe','Un event',4,6,'2022-02-02 18:00:00',NULL,'2022-01-12 15:19:44','2022-01-25 11:00:00',3,5,'Juste un event, bro !',27,0),(18,'6a64178b-b376-46ec-a0b4-7cfd4115a9c9','Un event',4,6,'2022-02-02 18:00:00','2022-02-02 22:59:59','2022-01-14 14:27:44','2022-01-25 11:00:00',3,5,'Juste un event, bro !',28,0),(19,'44744027-8cdc-4e58-a1ad-effffe43d81e','Un event',4,6,'2022-02-02 18:00:00',NULL,'2022-01-14 14:27:47','2022-01-25 11:00:00',3,5,'Juste un event, bro !',29,0),(20,'d08c0839-7470-43f8-ba06-a6525af19ab5','Un event',4,6,'2022-02-02 18:00:00','2022-02-02 22:59:59','2022-01-14 14:27:50','2022-01-25 11:00:00',3,5,'Juste un event, bro !',30,0),(21,'b0b0be26-a4de-41f9-895b-6ed62fae82e2','Un event',4,6,'2022-02-02 18:00:00',NULL,'2022-01-14 14:27:54','2022-01-25 11:00:00',3,5,'Juste un event, bro !',31,0);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `event_user`
--

LOCK TABLES `event_user` WRITE;
/*!40000 ALTER TABLE `event_user` DISABLE KEYS */;
INSERT INTO `event_user` VALUES (4,2),(11,3),(6,15),(6,18);
/*!40000 ALTER TABLE `event_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `event_user_in_queue`
--

LOCK TABLES `event_user_in_queue` WRITE;
/*!40000 ALTER TABLE `event_user_in_queue` DISABLE KEYS */;
INSERT INTO `event_user_in_queue` VALUES (6,2),(5,3),(6,3),(7,3),(20,3),(7,4),(6,11),(17,11);
/*!40000 ALTER TABLE `event_user_in_queue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `friend_list`
--

LOCK TABLES `friend_list` WRITE;
/*!40000 ALTER TABLE `friend_list` DISABLE KEYS */;
INSERT INTO `friend_list` VALUES (11,1),(11,4),(5,7);
/*!40000 ALTER TABLE `friend_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game` DISABLE KEYS */;
INSERT INTO `game` VALUES (1,'fecc2fb7-8af2-4f80-907e-bcac17739749','7 Wonders',3,7,30,30,10,'Antoine Bauza','Dans 7 Wonders, vous êtes à la tête de l\'une des sept grandes cités du monde antique. Votre but est de faire prospérer votre ville pour la rendre plus influente que celles de vos adversaires. Le futur des cités légendaires comme Babylone, Éphèse ou encore Rhodes dépend de vos talents de gestionnaire. Pour inscrire votre cité dans l\'Histoire, vous devrez agir dans différents secteurs de développement. Exploitez les ressources naturelles de vos terres, participez aux progrès scientifiques, développez vos relations commerciales et affirmez votre suprématie militaire. Laissez votre empreinte dans l\'histoire des civilisations en bâtissant une merveille monumentale. ','Dans 7 Wonders, vous êtes à la tête de l\'une des sept grandes cités du monde antique.'),(2,'5f0231d8-8fc6-4221-b5f9-ed36cf4750f5','Innovation',2,4,60,90,14,'Carl Chudyk','Menez votre civilisation à travers les grandes périodes de l\'histoire !\\nDe la Préhistoire à l\'Ere Numérique, guidez votre civilisation jusqu\'à son apogée !\\nAccumulez inventions et idées révolutionnaires pour vous développer et éclipser les autres joueurs.\\nTactique, opportunisme et audace seront les clefs de votre victoire ! ','De la Préhistoire à l\'Ere Numérique, guidez votre civilisation jusqu\'à son apogée ! '),(3,'aaba0a2d-cbdc-4192-a42a-c6135624c341','Myrmes',2,4,75,90,12,'Yoann Levet','Au royaume de Myrmes, les colonies des fourmis luttent depuis des siècles pour dominer leurs voisines.\\nPour mettre fin à cette guerre, le conseil des reines a décrété qu’une dernière bataille déciderait de la victoire finale.\\nLancez vos soldats et vos ouvrières à l’assaut du royaume. Remplissez votre réserve, chassez des insectes et placez vos phéromones judicieusement pour conquérir le territoire. Mais surtout, n’oubliez pas de préparer l’hiver !\\nChaque joueur dirige une colonie de fourmis du monde de Myrmes luttant pour établir sa suprématie sur le royaume. Au cours du jeu, les joueurs accumuleront des points en posant des tuiles phéromones, en chassant des proies, en nourrissant la Reine et en remplissant des objectifs. À la fin de la partie, le joueur avec le plus de points sera déclaré vainqueur.\\nLe jeu se déroule sur une période de trois années, chaque année est divisée en quatre saisons (printemps, été, automne, hiver). Les trois premières saisons sont des tours de jeu normaux, la dernière (hiver) est une phase de maintenance au cours de laquelle les joueurs doivent fournir de la nourriture pour passer l’hiver. ','Au royaume de Myrmes, les colonies des fourmis luttent depuis des siècles pour dominer leurs voisines.'),(4,'445d42eb-0985-45d0-80e5-582292a3a056','Root',1,6,60,120,10,'Cole Wehrle','Root est un jeu d\'aventure et de guerre dans lequel les joueurs incarnent une des quatre factions et luttent pour devenir le dirigeant le plus puissant de la vaste Forêt.\\nRoot est un jeu qui se joue et se raconte comme une histoire. Les joueurs dirigent le récit, et les différences entre chaque rôle créent un niveau important d\'interaction et de rejouabilité.\\nÊtes-vous prêt à vous battre pour le contrôle de cette vaste région sauvage ?','Root est un jeu d\'aventure et de guerre qui se joue et se raconte comme une histoire.'),(5,'f6b186a2-e604-4b82-a8b2-010f6df02de5','Mysterium',2,7,30,60,10,'Oleg Sidorenko, Oleksandr Nevskiy','M. MacDowell, astrologue doué, a détecté la présence d\'un être surnaturel en entrant dans sa nouvelle maison en Écosse. Il a réuni d\'éminents médiums pour une séance extraordinaire. Ces derniers ont sept heures pour contacter le fantôme et enquêter sur son assassinat. Malheureusement, le fantôme est amnésique et ne peut communiquer avec les médiums que par le biais de visions, qui sont représentées dans le jeu par des cartes illustrées. Les médiums doivent déchiffrer les images pour aider le fantôme à se rappeler du drame : Qui a commis le crime ? Où s\'est-il déroulé ? Quelle arme a causé la mort ? Plus les médiums coopèrent et devinent bien, plus il est facile d\'attraper le bon coupable.','Mysterium, un jeu coopératif mais asymétrique à l\'ambiance surnaturelle'),(6,'248efeb9-3d1a-4905-89b3-7289ac47c681','Inis',2,4,60,120,14,'Christian Martinez','À l’avant du navire, vous scrutez la brume qui se dissipe et révèle enfin les nouvelles terres tant attendues.\\nLa clameur se répand dans toute la flotte. Une nouvelle ère de prospérité s’offre à vos clans. Comme vous, d’autres chefs font partie de l’expédition et il sera bientôt l’heure du grand conseil qui choisira le Haut-Roi d’Inis.\\nFaites grandir l’influence de vos clans. Partez à la conquête de nouveaux territoires, imposez votre force brute et élevez au plus haut votre spiritualité. Canalisez la force des légendes qui imprègnent l’univers d’Inis et imposez-vous face à vos adversaires.\\nÊtes-vous celui qui méritera ce titre ?','Plongez au coeur des légendes celtiques.'),(8,'c8b4b920-6435-43a4-a114-865261aa3f57','7 Wonders : Duel',2,2,30,60,10,'Bruno Cathala, Antoine Bauza','De bien des façons, 7 Wonders Duel ressemble à son grand-frère 7 Wonders. Les joueurs acquièrent des cartes au cours de trois âges, ces dernières fournissant des ressources ou faisant progresser leur développement militaire ou scientifique afin de développer leur civilisation ou bâtir des merveilles.\\nCe qui est différent avec 7 Wonders Duel, c\'est que, comme le titre le suggère, le jeu est uniquement pour deux joueurs. Ces derniers ne tirent pas simultanément des cartes à partir de mains de cartes, mais à partir d\'un mélange de cartes face cachée et face visible disposées en formation pyramidale au début de chaque âge. Un joueur ne peut prendre une carte que si elle n\'est pas couverte par d\'autres. Le timing et les choix que vous allez faire sont cruciaux. Comme dans le jeu original, chaque carte que vous acquérez peut être construite, défaussée pour des pièces de monnaie ou utilisée pour construire une merveille.','7 Wonders Duel, l\'adaptation 2 joueurs'),(9,'5e1dac80-edd0-445c-8594-e2bfe0bb85f9','Kingsburg',2,5,90,90,10,'Andrea Chiarvesio, Luca Iennaco','Le royaume de Kingsburg est attaqué ! De monstrueuses armées sont à ses portes, cherchant sans relâche à envahir le royaume afin de tout y détruire. Vous avez été nommé gouverneur d’une province frontalière — c’est à vous de la défendre et de la faire prospérer, d’attirer les faveurs des membres de la Famille Royale et de leurs Conseillers, afin d’obtenir l’or, le bois, la pierre, mais également les soldats nécessaires à son essor et à sa protection. Attention, vous n’êtes pas seul : les autres Gouverneurs œuvreront de leur côté pour offrir le meilleur à leurs propres provinces.\\nÀ vous d’être plus perspicace qu’eux ! Vous devrez construire des bâtiments, consolider vos défenses et entraîner votre armée. Après cinq années, l’un seul des Gouverneurs sera nommé au Conseil du Roi et ce Gouverneur pourrait ou doit être vous !\\nAu fil du temps, de nouveaux territoires seront conquis, mais les vieux dangers, comme des nouveaux, n’auront de cesse de menacer les frontières. C’est pourquoi le Roi Tritus devra nommer de nouveaux Gouverneurs pour défendre et développer les régions bordant son royaume. Ils auront davantage de possibilités pour développer leurs provinces, tandis que de nouvelles routes plus sûres vers la capitale offriront une vision affinée des renforts sur lesquels compter lors des batailles hivernales à venir.','Le royaume de Kingsburg est attaqué !'),(10,'952e90de-fbaf-4a7c-ae0d-063d45ca32bf','Mafia de Cuba',6,12,30,45,12,'Loïc Lamy, Philippe des Pallières','La Havane, 29 décembre 1955. A la fin du repas offert à ses « fidèles » hommes de main, Don Alessandro évoque les « affaires » en cours quand retentit le téléphone de l’arrière salle du restaurant. Le parrain est convoqué au bureau du président Batista. Il confie sa précieuse boîte à cigares à ses sbires. Il faut dire que celle-ci possède un double fond, sous un premier rang de cigares se trouve une cachette remplie de diamants !\\nChaque joueur va prendre cette boîte, l’ouvrir et choisir de : trahir et voler quelques diamants, rester un fidèle et « honnête » mafioso, être un chauffeur, un tueur ou même un agent infiltré de la CIA (en s\'emparant du jeton correspondant). Le soir venu, le Parrain récupère sa boîte qui est passée de main en main. Il s’étouffe de rage et enquête sur la disparition des diamants. Il doit retrouver son trésor sans manquer de punir les coupables en leur offrant des chaussures en ciment avant de les jeter dans la baie.\\nAprès des débats houleux et des déductions périlleuses, le Parrain retrouvera-t-il tous ses diamants avec l’aide de ses fidèles ?\\nPerdra-t-il son honneur en accusant à tort ?\\nLe plus rusé des voleurs l’emportera-t-il ?\\nOu est ce la CIA qui enverra tous ces malfrats sous les verrous ? ','A la fin du repas offert à ses « fidèles » hommes de main, Don Alessandro évoque les « affaires » en cours quand retentit le téléphone de l’arrière salle du restaurant.'),(11,'867670c5-08cd-4ef1-8e86-7d9e24380229','Cluedo',3,6,20,45,8,'Anthony Pratt','Cluedo est un jeu d\'enquête dans lequel les joueurs devront résoudre un crime commis dans un somptueux manoir anglais. Déduction et observation seront vos meilleurs atouts pour résoudre cette affaire mystérieuse.\\n\"Le Colonel Moutarde dans la cuisine avec un chandelier\"! Qui n\'a jamais prononcé une accusation de ce type ? Depuis sa commercialisation en 1949, Cluedo a permis à des millions d\'enquêteurs en herbe de résoudre des crimes tout en s\'amusant.\\nAvec cette nouvelle édition, le célèbre jeu de société revient dans une version relookée : nouveau packaging et pions en couleur à l\'effigie des personnages. Même les décors du fameux manoir Tudor ont été retravaillés pour laisser place à une toute nouvelle expérience de jeu.','Une mystérieuse énigme à résoudre et des suspects connus.'),(12,'fc670aa6-3d5f-4621-8674-076a70175412','Pandémie',2,4,45,60,14,'Matt Leacock','Vous et votre compagnie êtes des membres très qualifiés d\'équipes de luttes contre les maladies menant une bataille contre quatre maladies mortelles. Votre équipe va parcourir le monde afin d\'enrayer la vague d\'infections et de développer les ressources dont vous aurez besoin pour découvrir les remèdes. Vous devez travailler ensemble, utiliser vos forces individuelles pour éradiquer les maladies avant qu\'elles ne dépassent le monde. Le temps presse alors que les foyers d’infection accélèrent la propagation de la peste. Allez-vous trouver les remèdes à temps ? Le sort de l\'humanité est entre vos mains! ','Vous et votre compagnie êtes des membres très qualifiés d\'équipes de luttes contre les maladies menant une bataille contre quatre maladies mortelles.'),(13,'76734c23-7dfa-4fcd-9730-44e0a65c4df9','Agricola',1,5,90,120,12,'Uwe Rosenberg','L\'Europe Centrale des années 1670. La peste qui sévissait depuis 1348 a finalement été vaincue. le monde civilisé est en reconstruction. Les hommes aménagent leurs chaumières et les rénovent. Les champs doivent être labourés, cultivés et finalement, moissonnés. Le souvenir de la famine des années noires a conduit l\'humanité à manger plus de viande (une habitude toujours en vigueur dans notre monde moderne).\\nÀ partir d\'un lopin de terre et d\'une modeste cabane en bois, les joueurs jouent le rôle d\'une famille de paysans qui vont tenter de prospérer afin d\'avoir, à la fin de la partie, la plus belle ferme. ','Agricola, le jeu de développement agricole ! '),(14,'649a5145-6c4a-4107-ae51-bb02921cde51','Hyperborea',2,6,90,120,12,'Pierluca Zizzi, Andrea Chiarvesio','Hyperborea est un jeu de civilisation pour joueurs aguerris, jouable en moins de deux heures et au système original dit de \"bag building\".\\nSix nations rivales sont nées des cendres de la civilisation hyperboréenne. Chaque nation tente aujourd’hui d’établir sa suprématie sur ses rivaux. À la tête de votre faction, vous seul pouvez mener votre peuple à la suprématie sur le monde d’Hyperborea. ','Un jeu de civilisation reposant sur le bag-building'),(15,'3f7419e9-91ba-4cb4-8814-8ba07e2244d3','Dixit',3,6,20,30,8,'Jean-Louis Roubira','Le principe de Dixit est simple : les joueurs doivent deviner et faire deviner des cartes illustrées. À chaque tour, un joueur devient le conteur qui choisit une carte et la décrit avec une phase, un mot ou un son. Mais attention, pour marquer des points, la carte doit être trouvée seulement par une partie des joueurs. Le message doit donc être à la fois clair et crypté. Un certain mystère doit planer. À vous d\'être inventif et malin ! En plus de devoir trouver le bon dessin, les autres joueurs doivent également choisir une carte dans leur main proche de la description du conteur. Le but ici est d\'induire les autres en erreur. Une fois toutes les cartes récupérées, elles sont dévoilées par le conteur. Les joueurs ont la tâche de débusquer l\'image du conteur.','Une image vaut mille mots !'),(16,'4397bf12-f3d1-4df5-8f21-6438b43a8ba0','Les Aventuriers du Rail',2,5,30,45,8,'Alan R. Moon','Dans ce jeu, replongez dans l\'âge d\'or du chemin de fer. Lancez-vous à la conquête du rail et tentez de prendre le contrôle du réseau ferroviaire américain en reliant un maximum de villes entre elles.\\nAu début de la partie, chaque joueur doit déterminer ses objectifs à atteindre. Les objectifs représentent une liaison entre deux villes que le joueur devra impérativement établir s\'il ne veut pas se voir pénaliser. Ces objectifs sont très importants pour le déroulement de la partie, car si un joueur parvient à le remplir, il gagnera des points bonus. Dans le cas contraire, il se verra infliger un malus et perdra des points. À chaque tour, les joueurs ont le choix entre 3 actions : piocher des objectifs, récupérer des cartes wagon, dépenser les cartes pour contrôler les lignes du chemin de fer.\\nUn joueur prend le contrôle d\'une ligne en utilisant des cartes de la même couleur que la voie. Une fois contrôlée, la ligne appartient au joueur et plus aucun participant ne peut en prendre le contrôle. À la fin de la partie, un bonus de 10 points est donné au joueur qui aura la plus grande ligne ferroviaire.','Prenez la main sur les voies de chemin de fer et bloquez les autres participants dans leur désir de conquête.'),(17,'c90c1878-a4bc-4624-b656-a0a5a69adb3d','6 qui prend !',2,10,30,30,10,'Wolfgang Kramer','6 qui prend est un jeu accessible et simple à comprendre. Il vous promet des belles parties pleines de rigolades et de rebondissements. Il est adapté aux enfants à partir de 8 ans et permet de jouer de deux à dix personnes. Les anniversaires et les fêtes de famille sont des moments idéaux pour le sortir !\\nLe principe du jeu est très facile à comprendre. Il y a 104 cartes numérotées dans le jeu. Sur certains cartes apparaissent des \"têtes de taureaux\", correspondants à des points négatifs. 4 cartes sont placées en colonne sur la table, face visible, puis 10 cartes sont distribuées à chaque joueur. Ensuite, les joueurs dévoilent simultanément une carte choisie dans leur main, qui sont placées sur la table d\'après certaines conditions à suivre. Il faut cependant faire attention...','Déposez vos bêtes à cornes dans la bonne rangée, et, surtout, n\'en ramassez aucune !'),(18,'31391ce9-801b-4012-bdf2-6251199e053b','L\'Âge de Pierre',2,4,60,90,10,'Bernd Brunnhofer, Michael Tummelhofer','À l’aube des civilisations, nos ancêtres travaillèrent sans relâche afin de survivre ne serait-ce qu’une journée. Mais le génie de l’homme avait déjà permis de faciliter leur vie. L’Âge de Pierre lance les joueurs dans cette époque rude et sans pitié. À l’aide d’outils, au départ rudimentaires, vous aurez à récolter bois, pierre et or. Ces ressources vous seront fort utiles dans vos négociations avec les villages voisins. Bien que la chance sera maître d’une partie de votre périple, une bonne planification vous permettra de prendre le dessus sur toutes circonstances hasardeuses sans oublier de nourrir votre peuple à chaque tour. Il vous faudra vous dépasser et vous débrouiller, tout comme vos ancêtres, afin d’atteindre la victoire! ','À l’aube des civilisations, nos ancêtres travaillèrent sans relâche afin de survivre ne serait-ce qu’une journée.'),(19,'bffab350-dee2-4db8-a326-16421bb6254a','Dominion',2,4,30,30,8,'Donald X. Vaccarino','Vous êtes le souverain d\'un paisible royaume verdoyant. Vous rêvez d’étendre ce royaume et de le rendre plus grand et plus riche. Il vous faut un véritable Dominion ! En unifiant sous votre bannière les nombreux fiefs au bord de l\'anarchie qui vous entourent, vous leur apporterez paix et civilisation. Mais d\'autres monarques partagent vos ambitions. Vous devrez agir promptement afin d\'acquérir le plus de terres possible ! \\n\nPour parvenir à vos fins, vous recruterez des laquais, vous construirez des bâtiments et vous entretiendrez le confort de votre château, sans oublier de remplir les coffres de votre trésorerie.\\nDans Dominion, chaque joueur commence avec un petit paquet de cartes identique. Au centre de la table se trouve une sélection d\'autres cartes que les joueurs peuvent \"acheter\" au fur et à mesure qu\'ils peuvent se le permettre. Grâce à leur sélection de cartes à acheter, et la façon dont ils jouent leurs mains, les joueurs construisent leur paquet à la volée, s\'efforçant de trouver le chemin le plus efficace vers les précieux points de victoire à la fin du jeu.','Étendez votre domaine ! '),(20,'4df6569c-4fed-4cf3-8269-bd45ff8d47c9','Splendor',2,4,30,45,10,'Marc André','Dans Splendor, vous dirigez une guilde de marchands.\\nA l’aide de jetons symbolisant des pierres précieuses, vous allez acquérir des développements qui produisent de nouvelles pierres (les bonus). Ces bonus réduisent le coût de vos achats et attirent les nobles mécènes.\\nChaque tour est rapide : une action et une seule ! Observez vos adversaires, anticipez et réservez les bonnes cartes.\\nLe premier joueur à parvenir à quinze points de prestige en cumulant nobles et développements déclenche la fin de la partie. ','Dans Splendor, vous dirigez une guilde de marchands.'),(21,'7d0b4582-4aed-43d6-8903-b6bac85dcdcd','Hanabi',2,5,30,30,8,'Antoine Bauza','Dans Hanabi, les joueurs incarnent une équipe d\'artificiers ayant la charge d\'organiser une représentation spectaculaire. C\'est le grand jour, tout est prêt. Mais voilà, un imprévu survient. Toutes les mèches ont été mélangées. Le public commence à affluer et il ne faut pas les décevoir. Il faut donc trouver le moyen de tout remettre dans l\'ordre afin de pouvoir tirer les magnifiques feux d\'artifice que tout le monde attend.\\nHanabi est un jeu de coopération entre les joueurs avec une mécanique de jeu inédite. Le jeu comprend 5 couleurs différentes de fusées (Bleu, rouge, blanc, vert et jaune). Le but du jeu est d\'aligner les feux d\'artifice de 1 à 5 dans chaque couleur.\\nChaque participant reçoit 4 cartes. Mais attention, il lui est interdit de les regarder. Il les montre aux autres artificiers qui doivent le guider en lui donnant les indices. Petit à petit, les différentes fusées seront classées et la représentation aura lieu pour le plus grand bonheur des spectateurs.\\nSi un joueur se trompe, l\'ensemble de l\'équipe perd un point de vie et la partie continue. Celle-ci prend fin si les artificiers perdent leurs 3 points de vie ou si tous les feux sont classés.','Dans ce jeu coopératif jamais vu, les joueurs œuvrent ensemble pour tirer de beaux feux d’artifice.'),(22,'d0244ddb-5088-496d-b8a6-31516f2ae360','Five Tribes',2,4,60,90,13,'Bruno Cathala','En traversant le pays des 1001 nuits, votre caravane arrive au légendaire sultanat de Naqala. Le vieux sultan vient de mourir et le contrôle de Naqala est à saisir ! Les oracles ont prédit des étrangers qui manœuvreraient les cinq tribus pour gagner de l\'influence sur la légendaire cité-État. Veux-tu accomplir la prophétie ? Invoquez les vieux Djinns et mettez les tribus en position au bon moment, et le Sultanat peut devenir le vôtre !','Après des journées de voyage au cœur du pays des mille et une nuits, votre caravane arrive enfin au fabuleux sultanat de Naqala.'),(23,'9f7731a6-01bf-456f-bb94-c81fd54e9c02','King of Tokyo',2,6,20,35,8,'Richard Garfield','Dans King of Tokyo , vous incarnez des monstres mutants, des robots gigantesques et d\'étranges extraterrestres, qui détruisent tous Tokyo et se bousculent afin de devenir le seul et unique roi de Tokyo.\\nAu début de chaque tour, vous lancez six dés, qui affichent les six symboles suivants: 1, 2 ou 3 points de victoire, Énergie, Soins et Attaque. Après trois lancers successifs, choisissez de garder ou de jeter chaque dé pour gagner des points de victoire, gagner de l\'énergie, restaurer la santé ou attaquer les autres joueurs en leur faisant comprendre que Tokyo est VOTRE territoire.\\nLe joueur le plus acharné occupera Tokyo et gagnera des points de victoire supplémentaires, mais ce joueur ne pourra pas guérir et devra affronter tous les autres monstres ! ','Lancez les dés, améliorez votre monstre et affrontez ceux des autres joueurs pour remporter le titre très convoité de King of Tokyo'),(24,'24125adb-fa1a-4301-a3a6-85999a336635','Horreur à Arkham 3e Édition',1,6,180,200,14,'Richard Launius, Kevin Wilson, Nikki Valens','Massachusetts, 1926. La ville d’Arkham vit depuis trop longtemps sur sa paisible île d’ignorance, au cœur des noirs océans de l’infini. Seule une poignée d’investigateurs malchanceux ose plonger dans cet abîme pour combattre les maux antédiluviens qui menacent la réalité depuis chaque recoin de cette bourgade de Nouvelle-Angleterre.\\nHorreur à Arkham est un jeu coopératif alliant mystère et terreur pour un à six joueurs. Inspiré des écrits de H.P. Lovecraft, chaque scénario unique vous mettra dans la peau d’un investigateur d’Arkham qui explore les rues de la ville et œuvre avec ses pairs pour sauver l’humanité d’indicibles horreurs. ','Plongez dans l’univers de H.P.Lovecraft'),(25,'2e795a46-2ec5-4171-942a-4f0980c40e81','Seasons',2,4,45,60,14,'Régis Bonnessée','Les plus grands sorciers du royaume se réunissent. À l\'issue de trois années de compétition acharnée, le nouvel Archimage du royaume de Xidit sera désigné.\\nDans une première phase, sélectionnez neuf cartes pouvoir en même temps que vos adversaires. Choisissez-les bien car elles seront déterminantes pour le reste de la partie.\\nDans une seconde phase, tirez le meilleur parti des dés spécifiques proposés à chaque saison.\\nRécoltez des énergies, usez d\'objets magiques ancestraux, invoquez de puissants familiers et accumulez suffisamment de cristaux pour l\'emporter !','Devenez un puissant mage en profitant des énergies qui changent chaque saison.');
/*!40000 ALTER TABLE `game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `game_tag`
--

LOCK TABLES `game_tag` WRITE;
/*!40000 ALTER TABLE `game_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `game_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Lille','59900','3 Boulevard de mons'),(2,'Paris','75005','52 Rue des Écoles'),(3,'Oloron sainte Marie','64400',NULL),(4,'Pau',NULL,'19 Boulevard des Pyrénées'),(5,'Bordeaux',NULL,NULL),(6,'Bordeaux','33000',NULL),(7,'Bordeaux','33000','3 rue Poyenne'),(22,'Lyon','69001','10 Rue Joseph Serlin'),(23,'Lyon','69001','10 Rue Joseph Serlin'),(24,'Lyon','69001','10 Rue Joseph Serlin'),(25,'Lyon',NULL,NULL),(26,'Lyon',NULL,NULL),(27,'Lyon',NULL,NULL),(28,'Lyon','69001','10 Rue Joseph Serlin'),(29,'Lyon','69001','10 Rue Joseph Serlin'),(30,'Lyon',NULL,NULL),(31,'Lyon',NULL,NULL);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `token_type`
--

LOCK TABLES `token_type` WRITE;
/*!40000 ALTER TABLE `token_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `token_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `type_tag`
--

LOCK TABLES `type_tag` WRITE;
/*!40000 ALTER TABLE `type_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `type_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Toto','01e55b4659645f43046d52617179bb71ce73201e','Toto','Super','toto@super.com',_binary 'hjmkfhgefdmigunyermoutmouic,oejniuctmgnhmti,gsudxngio,igsuit'),(2,'AlphaGamer','ba65b07de6914d1e13973775e1d056949fc598f6','Jean-Michel','de Petit','jmichel@petit.fr',_binary 'hmkqsnmkqdumixnrurixumnrqouwnfrmioxnuriqgorgixurnmiruxmir,fg'),(3,'Meeple','655d463111c9918877f2f8859b6441099b297d74','Nick','Teton','tnick@germany.de',_binary '6jt746j871je3768l6i7esd34g35g7esrg41jl38i671e34zf35e7f3681a7zu3k1yk37y8y71ez6'),(4,'Gandalf','3cfefd5cf5dfdb9f6745ef806c863e9fcfcbfb61','Grudugnan','Grumeleux','grugru@trouduculdumonde.fr',_binary 'j3l41,8vjg4ch368x7/**/kdghluqd57485eszthflsduqfkrh'),(5,'Papyrus','a95c40f310c47178537882d69dcfe6b8b1dcc080','Marie','Curry','mc@cuisineradioactive.com',_binary 'hkghrelkghremlgerqlhrg46517416743681768sfljugiqrgl'),(6,'LaPlanche','e0a92ec80c51f547c747b9fcdcac283cd8e54deb','Julie','d\'artichaut','julie@artichaut.fr',_binary 'fhgsdgfsdfsdhgfhdlfgrlfgdsqfgdhssg'),(7,'Bidulle','1b516c652801e95bfb5af35ead4a2308561995a4',NULL,NULL,'letest@letest.com',NULL),(11,'Malabar','f8e7166763183dafbacb12a793469711642c5af6',NULL,NULL,'yoyo@malabar.com',NULL),(14,'Tarte','f2d81a260dea8a100dd517984e53c56a7523d96942a834b9cdc249bd4e8c7aa9',NULL,NULL,'email@email.com',NULL),(15,'hhhhhhh','589f6feca8b16ba637fc8a8ea35eea5c224b27e0a65f306e19de14cb0398965a',NULL,NULL,'hhhh@hg.hy',NULL),(16,'LeGrand','589f6feca8b16ba637fc8a8ea35eea5c224b27e0a65f306e19de14cb0398965a',NULL,NULL,'wala@walou.com',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_game_list`
--

LOCK TABLES `user_game_list` WRITE;
/*!40000 ALTER TABLE `user_game_list` DISABLE KEYS */;
INSERT INTO `user_game_list` VALUES (11,4),(5,5),(11,5);
/*!40000 ALTER TABLE `user_game_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_like_list`
--

LOCK TABLES `user_like_list` WRITE;
/*!40000 ALTER TABLE `user_like_list` DISABLE KEYS */;
INSERT INTO `user_like_list` VALUES (5,6),(11,8);
/*!40000 ALTER TABLE `user_like_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_token`
--

LOCK TABLES `user_token` WRITE;
/*!40000 ALTER TABLE `user_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_token` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-17 14:56:18
