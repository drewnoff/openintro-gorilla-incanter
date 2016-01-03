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

;; **
;;; ## The normal distribution
;; **

;; **
;;; In your description of the distributions, did you use words like *bell-shaped* 
;;; or *normal*?  It's tempting to say so when faced with a unimodal symmetric 
;;; distribution.
;;; 
;;; To see how accurate that description is, we can plot a normal distribution curve 
;;; on top of a histogram to see how closely the data follow a normal distribution. 
;;; This normal curve should have the same mean and standard deviation as the data. 
;;; We'll be working with women's heights, so let's store them as a separate object 
;;; and then calculate some statistics that will be referenced later. 
;; **

;; @@
(def fhgtmean
  (s/mean (i/sel fdims :cols :hgt)))

(def fhgtsd
  (s/sd (i/sel fdims :cols :hgt)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.distribuions/fhgtsd</span>","value":"#'openintro.distribuions/fhgtsd"}
;; <=

;; **
;;; Next we make a density histogram to use as the backdrop and use the `add-lines` 
;;; function to overlay a normal probability curve. The difference between a 
;;; frequency histogram and a density histogram is that while in a frequency 
;;; histogram the *heights* of the bars add up to the total number of observations, 
;;; in a density histogram the *areas* of the bars add up to 1. The area of each bar 
;;; can be calculated as simply the height *times* the width of the bar. Using a 
;;; density histogram allows us to properly overlay a normal distribution curve over 
;;; the histogram since the curve is a normal probability density function.
;;; Frequency and density histograms both display the same exact shape; they only 
;;; differ in their y-axis. You can verify this by comparing the frequency histogram 
;;; you constructed earlier and the density histogram created by the commands below.
;; **

;; @@
(def fhgt-hist
  (c/histogram (i/sel fdims :cols :hgt)
               :density true
               :x-label "height"))

(def x (range 140 191))

(def y
  (map (partial dist/pdf (dist/normal-distribution fhgtmean fhgtsd)) 
       x))

(c/add-lines fhgt-hist x y)

(chart-view fhgt-hist)
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x6b223b1d \"org.jfree.chart.JFreeChart@6b223b1d\"], :opts nil}"}
;; <=

;; **
;;; After building the density histogram with the first command, we create the x- 
;;; and y-coordinates for the normal curve.  We chose the `x` range as 140 to 190 in 
;;; order to span the entire range of `fheight`.  To create `y`, we use `pdf` applied to `normal-distribution` to calculate the density of each of those x-values in a distribution that is normal
;;; with mean `fhgtmean` and standard deviation `fhgtsd`.  Then normal curve is added to the histogram.
;; **

;; **
;;; *2.  Based on the this plot, does it appear that the data follow a nearly normal 
;;;     distribution?*
;; **

;; @@

;; @@
