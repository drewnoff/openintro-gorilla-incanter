;; gorilla-repl.fileformat = 1

;; **
;;; ## The normal distribution
;;; 
;;; Original lab is available [here](http://htmlpreview.github.io/?https://github.com/andrewpbray/oiLabs-base-R/blob/master/normal_distribution/normal_distribution.html).
;;; You can download the data for this lab from [here](https://www.openintro.org/stat/data/?data=bdims).
;; **

;; @@
(ns openintro.distribuions)

(require '[incanter.core :as i]
         '[incanter.charts :as c]
         '[incanter.io :as iio]
         '[incanter.stats :as s]
         '[incanter.distributions :as dist])
(use 'incanter-gorilla.render :reload)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; In this lab we'll investigate the probability distribution that is most central
;;; to statistics: the normal distribution.  If we are confident that our data are 
;;; nearly normal, that opens the door to many powerful statistical methods.  Here 
;;; we'll use the graphical tools of Incanter to assess the normality of our data and also 
;;; learn how to generate random numbers from a normal distribution.
;; **

;; **
;;; ## The Data
;; **

;; **
;;; This week we'll be working with measurements of body dimensions.  This data set 
;;; contains measurements from 247 men and 260 women, most of whom were considered 
;;; healthy young adults.
;; **

;; @@
(def bdims (iio/read-dataset "../data/bdims.csv" :header true))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.distribuions/bdims</span>","value":"#'openintro.distribuions/bdims"}
;; <=

;; **
;;; Let's take a quick peek at the first few rows of the data.
;; **

;; @@
(println (i/head (i/sel bdims :cols (take 12 (i/col-names bdims)))))

(println (i/head (i/sel bdims :cols (drop 12 (i/col-names bdims)))))
;; @@
;; ->
;;; 
;;; | :bia.di | :bii.di | :bit.di | :che.de | :che.di | :elb.di | :wri.di | :kne.di | :ank.di | :sho.gi | :che.gi | :wai.gi |
;;; |---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------|
;;; |    42.9 |      26 |    31.5 |    17.7 |      28 |    13.1 |    10.4 |    18.8 |    14.1 |   106.2 |    89.5 |    71.5 |
;;; |    43.7 |    28.5 |    33.5 |    16.9 |    30.8 |      14 |    11.8 |    20.6 |    15.1 |   110.5 |      97 |      79 |
;;; |    40.1 |    28.2 |    33.3 |    20.9 |    31.7 |    13.9 |    10.9 |    19.7 |    14.1 |   115.1 |    97.5 |    83.2 |
;;; |    44.3 |    29.9 |      34 |    18.4 |    28.2 |    13.9 |    11.2 |    20.9 |      15 |   104.5 |      97 |    77.8 |
;;; |    42.5 |    29.9 |      34 |    21.5 |    29.4 |    15.2 |    11.6 |    20.7 |    14.9 |   107.5 |    97.5 |      80 |
;;; |    43.3 |      27 |    31.5 |    19.6 |    31.3 |      14 |    11.5 |    18.8 |    13.9 |   119.8 |    99.9 |    82.5 |
;;; |    43.5 |      30 |      34 |    21.9 |    31.7 |    16.1 |    12.5 |    20.8 |    15.6 |   123.5 |   106.9 |      82 |
;;; |    44.4 |    29.8 |    33.2 |    21.8 |    28.8 |    15.1 |    11.9 |      21 |    14.6 |   120.4 |   102.5 |    76.8 |
;;; |    43.5 |    26.5 |    32.1 |    15.5 |    27.5 |    14.1 |    11.2 |    18.9 |    13.2 |     111 |      91 |    68.5 |
;;; |      42 |      28 |      34 |    22.5 |      28 |    15.6 |      12 |    21.1 |      15 |   119.5 |    93.5 |    77.5 |
;;; 
;;; 
;;; | :nav.gi | :hip.gi | :thi.gi | :bic.gi | :for.gi | :kne.gi | :cal.gi | :ank.gi | :wri.gi | :age | :wgt |  :hgt | :sex |
;;; |---------+---------+---------+---------+---------+---------+---------+---------+---------+------+------+-------+------|
;;; |    74.5 |    93.5 |    51.5 |    32.5 |      26 |    34.5 |    36.5 |    23.5 |    16.5 |   21 | 65.6 |   174 |    1 |
;;; |    86.5 |    94.8 |    51.5 |    34.4 |      28 |    36.5 |    37.5 |    24.5 |      17 |   23 | 71.8 | 175.3 |    1 |
;;; |    82.9 |      95 |    57.3 |    33.4 |    28.8 |      37 |    37.3 |    21.9 |    16.9 |   28 | 80.7 | 193.5 |    1 |
;;; |    78.8 |      94 |      53 |      31 |    26.2 |      37 |    34.8 |      23 |    16.6 |   23 | 72.6 | 186.5 |    1 |
;;; |    82.5 |    98.5 |    55.4 |      32 |    28.4 |    37.7 |    38.6 |    24.4 |      18 |   22 | 78.8 | 187.2 |    1 |
;;; |    80.1 |    95.3 |    57.5 |      33 |      28 |    36.6 |    36.1 |    23.5 |    16.9 |   21 | 74.8 | 181.5 |    1 |
;;; |      84 |     101 |    60.9 |    42.4 |    32.3 |    40.1 |    40.3 |    23.6 |    18.8 |   26 | 86.4 |   184 |    1 |
;;; |    80.5 |      98 |      56 |    34.1 |      28 |    39.2 |    36.7 |    22.5 |      18 |   27 | 78.4 | 184.5 |    1 |
;;; |      69 |    89.5 |      50 |      33 |      26 |    35.5 |      35 |      22 |    16.5 |   23 |   62 |   175 |    1 |
;;; |    81.5 |    99.8 |    59.8 |    36.5 |    29.2 |    38.3 |    38.6 |    22.2 |    16.9 |   21 | 81.6 |   184 |    1 |
;;; 
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; We broke output into two print commands to avoid clutter in the screen.
;; **

;; **
;;; You'll see that for every observation we have 25 measurements, many of which are
;;; either diameters or girths.  A key to the variable names can be found at 
;;; [http://www.openintro.org/stat/data/bdims.php](http://www.openintro.org/stat/data/bdims.php),
;;; but we'll be focusing on just three columns to get started: weight in kg (`wgt`), 
;;; height in cm (`hgt`), and `sex` (`1` indicates male, `0` indicates female).
;;; 
;;; Since males and females tend to have different body dimensions, it will be 
;;; useful to create two additional data sets: one with only men and another with 
;;; only women.
;; **

;; @@
(def mdims (i/$where {:sex 1} bdims))

(def fdims (i/$where {:sex 0} bdims))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.distribuions/fdims</span>","value":"#'openintro.distribuions/fdims"}
;; <=

;; **
;;; *1.  Make a histogram of men's heights and a histogram of women's heights.  How 
;;;     would you compare the various aspects of the two distributions?*
;; **

;; @@

;; @@
