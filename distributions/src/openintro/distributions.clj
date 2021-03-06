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

;; **
;;; ## Evaluating the normal distribution
;; **

;; **
;;; Eyeballing the shape of the histogram is one way to determine if the data appear
;;; to be nearly normally distributed, but it can be frustrating to decide just how 
;;; close the histogram is to the curve. An alternative approach involves 
;;; constructing a normal probability plot, also called a normal Q-Q plot for 
;;; "quantile-quantile".
;; **

;; @@
(def fhgt-qq-plot
   (i/with-data fdims
    (c/qq-plot :hgt)))

(chart-view fhgt-qq-plot)
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x64777dcd \"org.jfree.chart.JFreeChart@64777dcd\"], :opts nil}"}
;; <=

;; **
;;; We can add normal quantile-quantile line to make it easier to judge
;; **

;; @@
(defn qq-y [x mean sd]
  (map #(s/quantile-normal % :mean mean :sd sd)
     (map #(s/cdf-normal (+ mean (* sd %)) :mean mean :sd sd)
          x)))

(def qq-x (range -3 3 0.1))

(c/add-lines fhgt-qq-plot qq-x (qq-y qq-x fhgtmean fhgtsd))

(chart-view fhgt-qq-plot)
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x64777dcd \"org.jfree.chart.JFreeChart@64777dcd\"], :opts nil}"}
;; <=

;; **
;;; You'll learn about `cdf-normal` a bit later in this lab.
;; **

;; **
;;; A data set that is nearly normal will result in a probability plot where the 
;;; points closely follow the line.  Any deviations from normality leads to 
;;; deviations of these points from the line.  The plot for female heights shows 
;;; points that tend to follow the line but with some errant points towards the 
;;; tails.  We're left with the same problem that we encountered with the histogram 
;;; above: how close is close enough?
;;; 
;;; 
;;; A useful way to address this question is to rephrase it as: what do probability 
;;; plots look like for data that I *know* came from a normal distribution?  We can 
;;; answer this by simulating data from a normal distribution using `sample-normal`.
;; **

;; @@
(def sim-norm
  (s/sample-normal (i/nrow fdims) :mean fhgtmean :sd fhgtsd))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.distribuions/sim-norm</span>","value":"#'openintro.distribuions/sim-norm"}
;; <=

;; **
;;; The argument indicates how many numbers you'd like to generate, which we 
;;; specify to be the same number of heights in the `fdims` data set using the 
;;; `nrow` function.  The two options determine the mean and standard 
;;; deviation of the normal distribution from which the simulated sample will be 
;;; generated.  We can take a look at the shape of our simulated data set, `sim-norm`, 
;;; as well as its normal probability plot.
;; **

;; **
;;; *3.  Make a normal probability plot of `sim_norm`.  Do all of the points fall on 
;;;     the line?  How does this plot compare to the probability plot for the real 
;;;     data?*
;; **

;; **
;;; Even better than comparing the original plot to a single plot generated from a normal distribution is to compare it to many more plots generated from the same distribution.
;; **

;; **
;;; *4.  Does the normal probability plot for `hgt` look similar to the plots 
;;;     created for the simulated data?  That is, do plots provide evidence that the
;;;     female heights are nearly normal?*
;;; 
;;; *5.  Using the same technique, determine whether or not female weights appear to 
;;;     come from a normal distribution.*
;; **

;; **
;;; ## Normal probabilities
;; **

;; **
;;; Okay, so now you have a slew of tools to judge whether or not a variable is 
;;; normally distributed.  Why should we care?
;;; 
;;; It turns out that statisticians know a lot about the normal distribution.  Once 
;;; we decide that a random variable is approximately normal, we can answer all 
;;; sorts of questions about that variable related to probability.  Take, for 
;;; example, the question of, "What is the probability that a randomly chosen young 
;;; adult female is taller than 6 feet (about 182 cm)?" (The study that published
;;; this data set is clear to point out that the sample was not random and therefore 
;;; inference to a general population is not suggested.  We do so here only as an
;;; exercise.)
;;; 
;;; If we assume that female heights are normally distributed (a very close 
;;; approximation is also okay), we can find this probability by calculating a Z 
;;; score and consulting a Z table (also called a normal probability table).  In Incanter, 
;;; this is done in one step with the function `cdf-normal`.
;; **

;; @@
(- 1 (s/cdf-normal 182 :mean fhgtmean :sd fhgtsd))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>0.004434386918686806</span>","value":"0.004434386918686806"}
;; <=

;; **
;;; Note that the function `cdf-normal` gives the area under the normal curve below a 
;;; given value, `x`, with a given mean and standard deviation.  Since we're 
;;; interested in the probability that someone is taller than 182 cm, we have to 
;;; take one minus that probability.
;;; 
;;; Assuming a normal distribution has allowed us to calculate a theoretical 
;;; probability.  If we want to calculate the probability empirically, we simply 
;;; need to determine how many observations fall above 182 then divide this number 
;;; by the total sample size.
;; **

;; @@
(float 
  (/ 
    (i/nrow (i/$where {:hgt {:gt 182}} fdims)) 
    (i/nrow fdims)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>0.0038461538</span>","value":"0.0038461538"}
;; <=

;; **
;;; Although the probabilities are not exactly the same, they are reasonably close. 
;;; The closer that your distribution is to being normal, the more accurate the 
;;; theoretical probabilities will be.
;; **

;; **
;;; *6.  Write out two probability questions that you would like to answer; one 
;;;     regarding female heights and one regarding female weights.  Calculate the 
;;;     those probabilities using both the theoretical normal distribution as well 
;;;     as the empirical distribution (four probabilities in all).  Which variable,
;;;     height or weight, had a closer agreement between the two methods?*
;; **

;; **
;;; * * *
;;; 
;;; ## On Your Own
;;; 
;;; -   Now let's consider some of the other variables in the body dimensions data 
;;;     set.  Using the figures at the end of the exercises, match the histogram to 
;;;     its normal probability plot.  All of the variables have been standardized 
;;;     (first subtract the mean, then divide by the standard deviation), so the 
;;;     units won't be of any help.  If you are uncertain based on these figures, 
;;;     generate the plots in Incanter to check.
;;; 
;;;     **a.** The histogram for female biiliac (pelvic) diameter (`bii.di`) belongs
;;;     to normal probability plot letter ____.
;;; 
;;;     **b.** The histogram for female elbow diameter (`elb.di`) belongs to normal 
;;;     probability plot letter ____.
;;; 
;;;     **c.** The histogram for general age (`age`) belongs to normal probability 
;;;     plot letter ____.
;;; 
;;;     **d.** The histogram for female chest depth (`che.de`) belongs to normal 
;;;     probability plot letter ____.
;;; 
;;; -   Note that normal probability plots C and D have a slight stepwise pattern.  
;;;     Why do you think this is the case?
;;; 
;;; -   As you can see, normal probability plots can be used both to assess 
;;;     normality and visualize skewness.  Make a normal probability plot for female 
;;;     knee diameter (`kne.di`).  Based on this normal probability plot, is this 
;;;     variable left skewed, symmetric, or right skewed?  Use a histogram to confirm 
;;;     your findings.
;; **

;; **
;;; ![histQQmatch](https://raw.githubusercontent.com/drewnoff/openintro-gorilla-incanter/master/distributions/resources/histQQmatch.png)
;; **
