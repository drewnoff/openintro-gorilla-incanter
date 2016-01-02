;; gorilla-repl.fileformat = 1

;; **
;;; # Introduction to data
;;; 
;;; Original lab is available [here](http://htmlpreview.github.io/?https://github.com/andrewpbray/oiLabs-base-R/blob/master/intro_to_data/intro_to_data.html).
;;; You can download the data for this lab from [here](https://www.openintro.org/stat/data/?data=cdc). 
;; **

;; @@
(ns openintro.intro-to-data)

(require '[incanter.core :as i]
         '[incanter.charts :as c]
         '[incanter.io :as iio]
         '[incanter.stats :as s])
(use 'incanter-gorilla.render :reload)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ## Getting started
;;; The Behavioral Risk Factor Surveillance System (BRFSS) is an annual telephone 
;;; survey of 350,000 people in the United States. As its name implies, the BRFSS 
;;; is designed to identify risk factors in the adult population and report 
;;; emerging health trends. For example, respondents are asked about their diet and 
;;; weekly physical activity, their HIV/AIDS status, possible tobacco use, and even
;;; their level of healthcare coverage. The BRFSS Web site 
;;; ([http://www.cdc.gov/brfss](http://www.cdc.gov/brfss)) contains a complete 
;;; description of the survey, including the research questions that motivate the 
;;; study and many interesting results derived from the data.
;;; 
;;; We will focus on a random sample of 20,000 people from the BRFSS survey 
;;; conducted in 2000. While there are over 200  variables in this data set, we will
;;; work with a small subset.
;;; 
;;; We begin by loading the data set of 20,000 observations into Incanter dataset.
;; **

;; @@
(def cdc (iio/read-dataset "../data/cdc.csv" :header true))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.intro-to-data/cdc</span>","value":"#'openintro.intro-to-data/cdc"}
;; <=

;; @@
(i/col-names cdc)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:genhlth</span>","value":":genhlth"},{"type":"html","content":"<span class='clj-keyword'>:exerany</span>","value":":exerany"},{"type":"html","content":"<span class='clj-keyword'>:hlthplan</span>","value":":hlthplan"},{"type":"html","content":"<span class='clj-keyword'>:smoke100</span>","value":":smoke100"},{"type":"html","content":"<span class='clj-keyword'>:height</span>","value":":height"},{"type":"html","content":"<span class='clj-keyword'>:weight</span>","value":":weight"},{"type":"html","content":"<span class='clj-keyword'>:wtdesire</span>","value":":wtdesire"},{"type":"html","content":"<span class='clj-keyword'>:age</span>","value":":age"},{"type":"html","content":"<span class='clj-keyword'>:gender</span>","value":":gender"}],"value":"[:genhlth :exerany :hlthplan :smoke100 :height :weight :wtdesire :age :gender]"}
;; <=

;; **
;;; This returns the names `genhlth`, `exerany`, `hlthplan`, `smoke100`, `height`, 
;;; `weight`, `wtdesire`, `age`, and `gender`. Each one of these variables 
;;; corresponds to a question that was asked in the survey.  For example, for 
;;; `genhlth`, respondents were asked to evaluate their general health, responding
;;; either excellent, very good, good, fair or poor. The `exerany` variable 
;;; indicates whether the respondent exercised in the past month (1) or did not (0).
;;; Likewise, `hlthplan` indicates whether the respondent had some form of health 
;;; coverage (1) or did not (0). The `smoke100` variable indicates whether the 
;;; respondent had smoked at least 100 cigarettes in her lifetime. The other 
;;; variables record the respondent's `height` in inches, `weight` in pounds as well
;;; as their desired weight, `wtdesire`, `age` in years, and `gender`.
;; **

;; **
;;; *1.  How many cases are there in this data set?  How many variables?*
;; **

;; **
;;; To identify the number of cases in this data set one could type
;; **

;; @@
(i/nrow cdc)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>20000</span>","value":"20000"}
;; <=

;; **
;;; and for number of variables just type
;; **

;; @@
(i/ncol cdc)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>9</span>","value":"9"}
;; <=

;; **
;;; We can have a look at the first few entries (rows) of our data with the command
;; **

;; @@
(print (i/head cdc))
;; @@
;; ->
;;; 
;;; |  :genhlth | :exerany | :hlthplan | :smoke100 | :height | :weight | :wtdesire | :age | :gender |
;;; |-----------+----------+-----------+-----------+---------+---------+-----------+------+---------|
;;; |      good |        0 |         1 |         0 |      70 |     175 |       175 |   77 |       m |
;;; |      good |        0 |         1 |         1 |      64 |     125 |       115 |   33 |       f |
;;; |      good |        1 |         1 |         1 |      60 |     105 |       105 |   49 |       f |
;;; |      good |        1 |         1 |         0 |      66 |     132 |       124 |   42 |       f |
;;; | very good |        0 |         1 |         0 |      61 |     150 |       130 |   55 |       f |
;;; | very good |        1 |         1 |         0 |      64 |     114 |       114 |   55 |       f |
;;; | very good |        1 |         1 |         0 |      71 |     194 |       185 |   31 |       m |
;;; | very good |        0 |         1 |         0 |      67 |     170 |       160 |   45 |       m |
;;; |      good |        0 |         1 |         1 |      65 |     150 |       130 |   27 |       f |
;;; |      good |        1 |         1 |         0 |      70 |     180 |       170 |   44 |       m |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; and similarly we can look at the last few by typing
;; **

;; @@
(print (i/tail cdc))
;; @@
;; ->
;;; 
;;; |  :genhlth | :exerany | :hlthplan | :smoke100 | :height | :weight | :wtdesire | :age | :gender |
;;; |-----------+----------+-----------+-----------+---------+---------+-----------+------+---------|
;;; | excellent |        1 |         1 |         0 |      71 |     195 |       190 |   43 |       m |
;;; | very good |        1 |         1 |         1 |      72 |     210 |       175 |   52 |       m |
;;; | very good |        1 |         1 |         0 |      71 |     180 |       180 |   36 |       m |
;;; | very good |        0 |         1 |         1 |      63 |     165 |       120 |   31 |       f |
;;; |      good |        0 |         1 |         1 |      69 |     224 |       224 |   73 |       m |
;;; |      good |        1 |         1 |         0 |      66 |     215 |       140 |   23 |       f |
;;; | excellent |        0 |         1 |         0 |      73 |     200 |       185 |   35 |       m |
;;; |      poor |        0 |         1 |         0 |      65 |     216 |       150 |   57 |       f |
;;; |      good |        1 |         1 |         0 |      67 |     165 |       165 |   81 |       f |
;;; |      good |        1 |         1 |         1 |      69 |     170 |       165 |   83 |       m |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; You could also look at *all* of the data frame at once by typing its name into 
;;; the console, but that might be unwise here.  We know `cdc` has 20,000 rows, so 
;;; viewing the entire data set would mean flooding your screen.  It's better to 
;;; take small peeks at the data with `head`, `tail` or the subsetting techniques 
;;; that you'll learn in a moment.
;; **

;; **
;;; ## Summaries and tables
;; **

;; **
;;; The BRFSS questionnaire is a massive trove of information.  A good first step in
;;; any analysis is to distill all of that information into a few summary statistics
;;; and graphics.  As a simple example, the function `quantile` returns a numerical 
;;; summary: minimum, first quartile, median, second quartile, and maximum. 
;;; For `weight` this is
;; **

;; @@
(s/quantile (i/sel cdc :cols :weight))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>(68.0 140.0 165.0 190.0 500.0)</span>","value":"(68.0 140.0 165.0 190.0 500.0)"}
;; <=

;; **
;;; here we are using `sel` function to get just a single column from the dataset.
;; **

;; **
;;; Incanter also has built-in functions to compute summary statistics one by one.  For 
;;; instance, to calculate the mean, median, and variance of `weight`, type
;; **

;; @@
(s/mean (i/sel cdc :cols :weight))
(s/median (i/sel cdc :cols :weight))
(s/variance (i/sel cdc :cols :weight))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>1606.4841535051935</span>","value":"1606.4841535051935"}
;; <=

;; **
;;; While it makes sense to describe a quantitative variable like `weight` in terms
;;; of these statistics, what about categorical data?  We would instead consider the
;;; sample frequency or relative frequency distribution.  The function `table` does
;;; this for you by counting the number of times each kind of response was given.
;;; For example, to see the number of people who have smoked 100 cigarettes in their
;;; lifetime, type
;; **

;; @@
(print (i/$rollup :count :total :smoke100 cdc))
;; @@
;; ->
;;; 
;;; | :smoke100 | :total |
;;; |-----------+--------|
;;; |         0 |  10559 |
;;; |         1 |   9441 |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; or instead look at the relative frequency distribution by typing
;; **

;; @@
(print (i/$rollup (fn [x] (/ (count x) 20000)) :freq :smoke100 cdc))
;; @@
;; ->
;;; 
;;; | :smoke100 |       :freq |
;;; |-----------+-------------|
;;; |         0 | 10559/20000 |
;;; |         1 |  9441/20000 |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; the numbers in the results are presented as rational numbers. To convert them to IEEE floating-point numbers type
;; **

;; @@
(float (/  10559 20000))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>0.52795</span>","value":"0.52795"}
;; <=

;; **
;;; Next, we make a bar plot of the entries in the table by putting the table inside the `bar-chart` command.
;; **

;; @@
(def smoke100-chart
  (i/with-data
    (i/$rollup :count :total :smoke100 cdc)
    (c/bar-chart :smoke100 :total)))

(chart-view smoke100-chart)
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x69ac63f2 \"org.jfree.chart.JFreeChart@69ac63f2\"], :opts nil}"}
;; <=

;; **
;;; Notice what we've done here! We've computed the dataset of `smoke100` distribution and then
;;; immediately applied the graphical function, `bar-chart`. You could also break this into two steps by 
;;; typing the following:
;; **

;; @@
(def smoke (i/$rollup :count :total :smoke100 cdc))

(def smoke100-chart
  (i/with-data smoke
    (c/bar-chart :smoke100 :total)))

(chart-view smoke100-chart)
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x3ae2f7b \"org.jfree.chart.JFreeChart@3ae2f7b\"], :opts nil}"}
;; <=

;; **
;;; *2.  Create a numerical summary for `height` and `age`, and compute the interquartile range for each. Compute the relative frequency distribution for `gender` and `exerany`. How many males are in the sample? What proportion of the sample reports being in excellent health?*
;; **

;; **
;;; The `rollup` and `bar-chart` commands can be used to build multivariable bar charts. For example, to examine which participants have smoked across each gender, we could use the following.
;; **

;; @@
(def gender-smoke (i/$rollup :count :total [:smoke100 :gender] cdc))
(print gender-smoke)
;; @@
;; ->
;;; 
;;; | :smoke100 | :gender | :total |
;;; |-----------+---------+--------|
;;; |         0 |       m |   4547 |
;;; |         1 |       f |   4419 |
;;; |         0 |       f |   6012 |
;;; |         1 |       m |   5022 |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(def gender-smoke-chart
  (i/with-data gender-smoke
    (c/bar-chart :gender :total
                 :group-by :smoke100
                 :legend true)))

(chart-view gender-smoke-chart)
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x1059e95c \"org.jfree.chart.JFreeChart@1059e95c\"], :opts nil}"}
;; <=

;; **
;;; Also we can customize this chart using JFreeChart API
;; **

;; @@
(import org.jfree.chart.renderer.category.LayeredBarRenderer
        org.jfree.util.SortOrder)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-class'>org.jfree.util.SortOrder</span>","value":"org.jfree.util.SortOrder"}
;; <=

;; @@
(doto (.getPlot gender-smoke-chart)
     (.setRenderer (doto (LayeredBarRenderer.)
                    (.setDrawBarOutline false)))
     (.setRowRenderingOrder SortOrder/DESCENDING))
(chart-view gender-smoke-chart)
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0xd6d85d3 \"org.jfree.chart.JFreeChart@d6d85d3\"], :opts nil}"}
;; <=

;; **
;;; *3.  What does grouped bar chart reveal about smoking habits and gender?*
;; **

;; **
;;; ## Interlude: How Incanter thinks about data
;; **

;; **
;;; Incanter stores data in datasets, which you might think of as a 
;;; type of spreadsheet. Each row is a different observation (a different respondent)
;;; and each column is a different variable (the first is `genhlth`, the second 
;;; `exerany` and so on). To see the size of the dataset we can type
;; **

;; @@
(i/dim cdc)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-unkown'>20000</span>","value":"20000"},{"type":"html","content":"<span class='clj-unkown'>9</span>","value":"9"}],"value":"[20000 9]"}
;; <=

;; **
;;; which will return the number of rows and columns. Now, if we want to access a subset of the full data frame. For example, to see the sixth variable of the 567<sup>th</sup> row, use the format
;; **

;; @@
(i/sel cdc :rows 567 :cols 5)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-long'>172</span>","value":"172"}
;; <=

;; **
;;; which means we want the element of our data set that is in the 567<sup>th</sup>
;;; row (meaning the 568<sup>th</sup> (zero-based numbering) person or observation) and the 5<sup>th</sup> 
;;; column (in this case, weight). We know that `weight` is the 5<sup>th</sup> variable (zero-based numbering)
;;; because it is the 5<sup>th</sup> entry (starting from 0) in the list of variable names
;; **

;; **
;;; To see the weights for the first 10 respondents we can type
;; **

;; @@
(i/sel cdc :rows (range 10) :cols 5)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>175</span>","value":"175"},{"type":"html","content":"<span class='clj-long'>125</span>","value":"125"},{"type":"html","content":"<span class='clj-long'>105</span>","value":"105"},{"type":"html","content":"<span class='clj-long'>132</span>","value":"132"},{"type":"html","content":"<span class='clj-long'>150</span>","value":"150"},{"type":"html","content":"<span class='clj-long'>114</span>","value":"114"},{"type":"html","content":"<span class='clj-long'>194</span>","value":"194"},{"type":"html","content":"<span class='clj-long'>170</span>","value":"170"},{"type":"html","content":"<span class='clj-long'>150</span>","value":"150"},{"type":"html","content":"<span class='clj-long'>180</span>","value":"180"}],"value":"(175 125 105 132 150 114 194 170 150 180)"}
;; <=

;; **
;;; In this expression, we have asked just for rows in the range from 0 (inclusive) to 10 (exclusive).
;; **

;; @@
(range 10)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>(0 1 2 3 4 5 6 7 8 9)</span>","value":"(0 1 2 3 4 5 6 7 8 9)"}
;; <=

;; **
;;; To get all the columns we could pass `:all` keyword for `:cols` option or just omit this option. Similarly for the `:rows` we could access all the observations, not just the 567<sup>th</sup>, or rows 0 
;;; through 9. Try the following to see the weights for all 20,000 respondents fly 
;;; by on your screen
;; **

;; @@
;; (print (i/sel cdc :cols 5))
;; @@

;; **
;;; Recall that column 5 represents respondents’ weight, so the command above reported all of the weights in the dataset. An alternative method to access the weight data is by referring to the column name. Previously, we typed names(cdc) to see all the columns contained in the cdc dataset. We can use any of the column names to select items in our dataset.
;; **

;; @@
;; (print (i/sel cdc :cols :weight))
;; @@

;; **
;;; Since the result is a `clojure.lang.LazySeq`, we can subset it with just a single index. We see the weight for the 568<sup>th</sup> respondent by typing
;; **

;; @@
(nth (i/sel cdc :cols :weight) 567)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-long'>172</span>","value":"172"}
;; <=

;; **
;;; Similarly, for just the first 10 respondents
;; **

;; @@
(take 10 (i/sel cdc :cols :weight))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>175</span>","value":"175"},{"type":"html","content":"<span class='clj-long'>125</span>","value":"125"},{"type":"html","content":"<span class='clj-long'>105</span>","value":"105"},{"type":"html","content":"<span class='clj-long'>132</span>","value":"132"},{"type":"html","content":"<span class='clj-long'>150</span>","value":"150"},{"type":"html","content":"<span class='clj-long'>114</span>","value":"114"},{"type":"html","content":"<span class='clj-long'>194</span>","value":"194"},{"type":"html","content":"<span class='clj-long'>170</span>","value":"170"},{"type":"html","content":"<span class='clj-long'>150</span>","value":"150"},{"type":"html","content":"<span class='clj-long'>180</span>","value":"180"}],"value":"(175 125 105 132 150 114 194 170 150 180)"}
;; <=

;; **
;;; ## A little more on subsetting
;; **

;; **
;;; It's often useful to extract all individuals (cases) in a dataset that have specific characteristics. We accomplish this with Incanter's query language using `$where` function. 
;; **

;; **
;;; Suppose we want to extract just the data for the men in the sample. We can use the Incanter function `$where` to do that for us. For example, the command
;; **

;; @@
(def mdata
  (i/$where {:gender "m"} cdc))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.intro-to-data/mdata</span>","value":"#'openintro.intro-to-data/mdata"}
;; <=

;; **
;;; will create a new dataset called `mdata` that contains only the men from the `cdc` data set. In addition to finding it in your workspace alongside its dimensions, you can take a peek at the first several rows as usual
;; **

;; @@
(print (i/head mdata))
;; @@
;; ->
;;; 
;;; |  :genhlth | :exerany | :hlthplan | :smoke100 | :height | :weight | :wtdesire | :age | :gender |
;;; |-----------+----------+-----------+-----------+---------+---------+-----------+------+---------|
;;; |      good |        0 |         1 |         0 |      70 |     175 |       175 |   77 |       m |
;;; | very good |        1 |         1 |         0 |      71 |     194 |       185 |   31 |       m |
;;; | very good |        0 |         1 |         0 |      67 |     170 |       160 |   45 |       m |
;;; |      good |        1 |         1 |         0 |      70 |     180 |       170 |   44 |       m |
;;; | excellent |        1 |         1 |         1 |      69 |     186 |       175 |   46 |       m |
;;; |      fair |        1 |         1 |         1 |      69 |     168 |       148 |   62 |       m |
;;; | excellent |        1 |         0 |         1 |      66 |     185 |       220 |   21 |       m |
;;; | excellent |        1 |         1 |         1 |      70 |     170 |       170 |   69 |       m |
;;; |      fair |        1 |         0 |         0 |      69 |     170 |       170 |   23 |       m |
;;; |      good |        1 |         1 |         1 |      73 |     185 |       175 |   79 |       m |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; This new data set contains all the same variables but just under half the rows.
;;; It is also possible to tell Incanter to keep only specific variables, which is a topic
;;; we'll discuss in a future lab. For now, the important thing is that we can carve
;;; up the data based on values of one or more variables.
;; **

;; **
;;; You can use conjunction of several conditions
;; **

;; @@
(def m_and_over30 
  (i/$where {:gender "m" :age {:gt 30}} cdc))

(print (i/head m_and_over30))
;; @@
;; ->
;;; 
;;; |  :genhlth | :exerany | :hlthplan | :smoke100 | :height | :weight | :wtdesire | :age | :gender |
;;; |-----------+----------+-----------+-----------+---------+---------+-----------+------+---------|
;;; |      good |        0 |         1 |         0 |      70 |     175 |       175 |   77 |       m |
;;; | very good |        1 |         1 |         0 |      71 |     194 |       185 |   31 |       m |
;;; | very good |        0 |         1 |         0 |      67 |     170 |       160 |   45 |       m |
;;; |      good |        1 |         1 |         0 |      70 |     180 |       170 |   44 |       m |
;;; | excellent |        1 |         1 |         1 |      69 |     186 |       175 |   46 |       m |
;;; |      fair |        1 |         1 |         1 |      69 |     168 |       148 |   62 |       m |
;;; | excellent |        1 |         1 |         1 |      70 |     170 |       170 |   69 |       m |
;;; |      good |        1 |         1 |         1 |      73 |     185 |       175 |   79 |       m |
;;; |      good |        0 |         0 |         1 |      67 |     156 |       150 |   47 |       m |
;;; |      fair |        0 |         1 |         1 |      71 |     185 |       185 |   76 |       m |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; This will give you the data for men over the age of 30.
;; **

;; **
;;; To use disjunction of several conditions you can do this
;; **

;; @@
(def m_or_under30
  (i/$where (i/$fn [gender age] (or (= gender "m") (< age 30))) cdc))

(print (i/head m_or_over30))
;; @@
;; ->
;;; 
;;; |  :genhlth | :exerany | :hlthplan | :smoke100 | :height | :weight | :wtdesire | :age | :gender |
;;; |-----------+----------+-----------+-----------+---------+---------+-----------+------+---------|
;;; |      good |        0 |         1 |         0 |      70 |     175 |       175 |   77 |       m |
;;; | very good |        1 |         1 |         0 |      71 |     194 |       185 |   31 |       m |
;;; | very good |        0 |         1 |         0 |      67 |     170 |       160 |   45 |       m |
;;; |      good |        0 |         1 |         1 |      65 |     150 |       130 |   27 |       f |
;;; |      good |        1 |         1 |         0 |      70 |     180 |       170 |   44 |       m |
;;; | excellent |        1 |         1 |         1 |      69 |     186 |       175 |   46 |       m |
;;; |      fair |        1 |         1 |         1 |      69 |     168 |       148 |   62 |       m |
;;; | excellent |        1 |         0 |         1 |      66 |     185 |       220 |   21 |       m |
;;; | excellent |        1 |         1 |         1 |      70 |     170 |       170 |   69 |       m |
;;; |      fair |        1 |         0 |         0 |      69 |     170 |       170 |   23 |       m |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; This will take people who are men or under the age of 30 (why that's an interesting 
;;; group is hard to say, but right now the mechanics of this are the important 
;;; thing).
;; **

;; **
;;; *4. Create a new object called `under23_and_smoke` that contains all observations 
;;;     of respondents under the age of 23 that have smoked 100 cigarettes in their 
;;;     lifetime. Write the command you used to create the new object as the answer
;;;     to this exercise.*
;; **

;; **
;;; ## Quantitative data
;; **

;; **
;;; With our subsetting tools in hand, we'll now return to the task of the day: 
;;; making basic summaries of the BRFSS questionnaire.  We've already looked at 
;;; categorical data such as `smoke` and `gender` so now let's turn our attention to
;;; quantitative data.  Two common ways to visualize quantitative data are with box
;;; plots and histograms.  We can construct a box plot for a single variable with 
;;; the following command.
;; **

;; @@
(chart-view
  (c/box-plot (i/sel cdc :cols :height)
              :y-label "height"))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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sIWuU8M2Wv/4pZdeog8dIEKFrn3md193nVy/fXvEydzF1VddJU999rMIHRB6MIWuq7LpNqe6vKr+wutjFz/+8Y/l2LFjMjg4iNABIlTo/3DXXeZWe6TK3IV2Bfzi/vsROiD0YPeh62psL7zwAqPcAaJI6C9+6Uty165dES9zF9olcOarX0XogNBDMW1Nhagrs60EoQNEltB1ENzHnZV5KOeZB4J7du9G6IDQgyn0iYkJM9Jdb7V760dH6ACRJXS91X5TFNxqX4kudpMThulsCB3iRui6f/l6A+MQOkDkCF3XZr85CmXuubmLDuZD6IDQgyB0HRynv/BHjx41W5ieP39+GQgdIHKEvvfuu+W2a6+NWqFrV8EPP/c5hA4IPRhCdy0gMz4+zqA4gAgWula2n7jmmqiVuQvdY93+yCMIHRB6IIQ+NDQk7777ruHcuXNm6lptba37NU8QOkBkCP2F++4z886jXeh6h+HYn/4pQgeEHgih6+31jRaV2Uwf+tLSkly+fFkcDgdCB4QeYO678UazUEu0C133ZX/wttsQOiD0SBS6CryqqkpSUlJk//79cvbsWfN6W1ub12uutT68LjfreZ6uZofQAaH/7nb7dRG8ItxmpP7R3/wNQgeEvlWhz8/Py/T0tE/4+gGhpKTE/UNope76r8rexfDwsJkep//+WkIfGBhwn+/PTm8IHWJZ6L/8kz8xu6nFitB1/fm6P/9zhA4IPRgLy2w2Wm1rH/zo6OiG5x45ckTq6urWPK5C3+xyswgdYlno37/nHrl9586YEboO7vt/996L0AGhB1Loeqtcb5N74+DBg/LLX/7SDKJbKzrVTc89efKkHDp0SI4fPy5TU1OrztMfUG/Jr1f1q9C10q+urjbXReiA0H/H1267zWxJGitCVx69806EDgg9kELXVeI26kd//vnn5cMPP/T69adPn5bMzEwzWr6/v1+Kiork8OHDq84rLy83i9isl6amJmltbZWGhgZJS0szW7muzHr9+3qLHyCUhEron//Yx2JK5sq9IVoKVt8jflch1IR1pbj6+nqzD7qit8b1NZVwfn6+eayvrSX04uJi93OLxWLO99xHXT+tJCcni81m8/n70v3addEbKnSgQv9GVC8msxb/4ZprqNCBCj2QQtdqWmXrOdVMB625hD4zMyPPPffcmnLt6emR3NzcVUL3vLWu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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x4f8aa6e7 \"org.jfree.chart.JFreeChart@4f8aa6e7\"], :opts nil}"}
;; <=

;; **
;;; You can compare the locations of the components of the box by examining the 
;;; summary statistics.
;; **

;; @@
(s/quantile (i/sel cdc :cols :height))
(s/mean (i/sel cdc :cols :height))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>67.1829</span>","value":"67.1829"}
;; <=

;; **
;;; Confirm that the median and upper and lower quartiles reported in the numerical 
;;; summary match those in the graph. The purpose of a boxplot is to provide a 
;;; thumbnail sketch of a variable for the purpose of comparing across several 
;;; categories. So we can, for example, compare the heights of men and women with
;; **

;; @@
(chart-view 
  (doto 
    (c/box-plot 
      (i/sel (i/$where {:gender "m"} cdc) :cols :height)
      :legend true
      :y-label "height"
      :series-label "m")
    (add-box-plot 
      (i/sel (i/$where {:gender "f"} cdc) :cols :height)
      :series-label "f")
    ))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x4414516b \"org.jfree.chart.JFreeChart@4414516b\"], :opts nil}"}
;; <=

;; **
;;; or with more suitable way when you have many options to compare (you'll see in a moment)
;; **

;; @@
(def gr-gender (i/$group-by :gender cdc))

(chart-view
  (reduce (fn [bp [dseq slabel]]
            (add-box-plot bp dseq :series-label slabel))
          (box-plot 
            (i/sel (first (vals gr-gender)) :cols :height)
            :y-label "height"
            :series-label ((first (keys gr-gender)) :gender)
            :legend true)
          (rest (map (fn [[k v]]
                       [(i/sel v :cols :height) (k :gender)])
                     (seq gr-gender)) 
                )))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x43317405 \"org.jfree.chart.JFreeChart@43317405\"], :opts nil}"}
;; <=

;; **
;;; Next let's consider a new variable that doesn't show up directly in this data 
;;; set: Body Mass Index (BMI) 
;;; ([http://en.wikipedia.org/wiki/Body_mass_index](http://en.wikipedia.org/wiki/Body_mass_index)). 
;;; BMI is a weight to height ratio and can be calculated as:
;;; 
;;; $$ BMI = \frac{weight~(lb)}{height~(in)^2} * 703 $$
;;; 
;;; 703 is the approximate conversion factor to change units from metric (meters and 
;;; kilograms) to imperial (inches and pounds).
;;; 
;;; The following lines first make a new dataset called `cdc-bmi` containing new column with calculated bmi values and then create box plots of these values, defining groups by the values of `genhlth`.
;; **

;; @@
(def cdc-bmi
  (i/add-column
    :bmi
    (i/$= 2 * (i/sel cdc :cols :weight) / (i/sel cdc :cols :height) ** 2)
    cdc))

(def gr-genhlth
  (i/$group-by :genhlth cdc-bmi))

(chart-view
  (reduce (fn [bp [dseq slabel]]
            (add-box-plot bp dseq :series-label slabel))
          (box-plot 
            (i/sel (first (vals gr-genhlth)) :cols :bmi)
            :y-label "bmi"
            :series-label ((first (keys gr-genhlth)) :genhlth)
            :legend true)
          (rest (map (fn [[k v]]
                       [(i/sel v :cols :bmi) (k :genhlth)])
                     (seq gr-genhlth)) 
                )))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x529094d2 \"org.jfree.chart.JFreeChart@529094d2\"], :opts nil}"}
;; <=

;; **
;;; Notice that the first block above is just some arithmetic, but it's applied to 
;;; all 20,000 numbers in the `cdc` data set. That is, for each of the 20,000 
;;; participants, we take their weight, divide by their height-squared and then 
;;; multiply by 703. The result is 20,000 BMI values, one for each respondent. This lets us perform computations like this using very simple expressions.
;; **

;; **
;;; *5.  What does this box plot show? Pick another categorical variable from the 
;;;     data set and see how it relates to BMI. List the variable you chose, why you
;;;     might think it would have a relationship to BMI,  and indicate what the 
;;;     figure seems to suggest.*
;; **

;; **
;;; Finally, let's make some histograms. We can look at the histogram for the age of
;;; our respondents with the command
;; **

;; @@
(chart-view
  (c/histogram (i/sel cdc :cols :age)
               :title "Histogram of ages"
               :x-label "age"
               :y-label "frequency"))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,iVBORw0KGgoAAAANSUhEUgAAAfQAAAE1CAYAAAARYhKbAAApXklEQVR42u2d+VcTWRqG5++eX6ZFWUU2FbBFaJvVbrVlVURFBodNQbGhQYVWFBEYQFZZe+743tNVpxKSkBWq4HnPqSPJjcmbr6q+J3f/h0EIIYRQ4PUPQoAQQggBdIQQQggBdIQQQggBdIQQQggBdBRsLS0tmbm5OXv897//JSAB0dramvnw4YOZnp62525/f5+gIATQUdClpP7w4UP32NraOvSa3t5et3xoaMh9/vr16+aHH36wR3l5ecKfvbu7a9bX1+2xsbHByciwFhYWTFVVlXvOnOOPP/4gOAgBdBR0PX36NCS5q9YdrpKSErdcQEgX0Ht6etz/n5eXx8nIoA4ODsyVK1cOwRygIwTQEUA3r169srV3HSMjIwDdx1ITu/c8d3V1mc+fP5v379+b1dVVAoQQQEdnGejxSM3pHz9+NPPz8+bbt29JA31vb8/29wpAm5ubR37uysqKmZ2dNdvb22mJ0//+9z8LPgeCi4uLCb+3Yvv169eItWe9b7LdDvHERj+4nFhfuHDBfmamv3sy5+Cvv/6y76+uIP1/fXay1xdCAB0B9DiB/ssvv1g46GhoaAgBVF9fnyksLDzUvKum+Xfv3pkbN26YrKyskDLnvXQIBJIAWFtbe+h9iouLzeTk5CGveu9Lly65rzt37pxpbGwMeW/BItp3EFTv3btnf2CoTFILRE5OTsTm6ps3b9rar1fh7/nmzRtTVFQUEgN9juL04MEDk52d7Za1trYeCTFH8cYmVqzv378f8zMS/e7JnANJYzfu3LlzyGd+fr5tCQr/AXTU9YUQQEdnHuhjY2Pm06dPIYc3cXqBriTtPC+wOPrtt98iAsA59Jk//vhjzNfoczWIywu7SIeA40jgjPVa55iamor4HUpLS01BQYH7+NatW/Y1bW1tMd9PHuU10ntG8684RotBOMAiKZHYxIq1vlssJfrdkzkHqv17467j/PnzIY+9gzHjub4QAujozAP9qOMooKu51Pv6zs5OW+tfXl62zb6qQekzx8fHbe3VCwYlbedQ03F1dbVbrhru27dvbbNvU1NTSE1TTbNqqi0rKwv5bHkaHR01jx8/jgvo4YdTQ3/06JGFiAaPCV6qXYa/p75ntPesrKy0/z8StH799VdTV1cX8ry+31FKJDaKtb5LOCB1RKphe5XId0/2HHivA/2IUsuDms+9PyZUU9esiHivL4QAOgLoKQJdYPG+/tmzZyHznJ3+WClWH7r6RL3v8/r1a7dsZ2cnpBlY0BHMvK/XFDtHglo8QFcz8fPnz21/tCAheESSmnz1Hbyjxr0tFN73VHO3I4HV60OPIwFaPwBiKdHYSIODgyFN4Mkq1ndP5hwozt7nvesZCOzhrTaJXF8IAXR0poGu/mPVerxHbm5u3EBXLUo1w/D+WoFNANcALkexgK6mf+97hCdpvZ+3VieoeV+vwVuJAt0L5XCpKVmx8faFe4+ampoj31M1XO//EZwcNTc3u8+rlhtLicYmVaDH+92TOQcTExMhz+tHlffwlqmmn8j1hRBAR2ca6IkMiosGLr2noBEp+WvAlmrARwH9P//5T8j/C0/U3s++du2aGRgYCHm9aqrpAroGroV/D9WCvX3Y8QA9vDbqBbq3efkooCcam1SAnsh3T+YcaHBbvK1D+g6JXF8IAXQE0FMEuqRmYfXbXrx48VDSvXv3bsI1dC1X6pW3iVr9xurjjfY9UgF6eK1aLRaOF43KTgTo6uf1vpd3NHZ7e7v7/OXLlxOqoR8Vm2SBnuh3T+YceLsh5EvN9urqiHR4p6XFc30hBNARQE8B6F++fDk0F1pg8K4qp6ZUSaOwvQPENKjKm7Cj9TerRqofAN5FUvS53tdrIJcz/Su87zoRoGvgmBc43v7akwJ6orFJFuiJfvdkzkH4ILdoI/ydefqJXF8IAXQE0FMAusChPneNalbTp2pVSsJXr151X/vTTz/Z1/7++++HaoAa+KSkrpqgd91xedAIa8FMI8O9PwQcz+HTs9QsHD6yPFGgDw8PH5oKptqmasne2uFxAl1KNDbJAD2Z757MOdB8dm9/uAby/fnnnxbiukY0DkCetYBMItcXQgAdAfQUgR6rH1SQmZmZsa/VKl/R5lIL7ErU4QOgwo/u7m7XjxK+t3bqbc5PFugacBbNo3cRlOMGeqKxSQboyXz3ZM6BfrxFgn744QA93usLIYCOAHoKQFetSjWuSIOWVHNSUvZKI6i97+8ceh8HggJGeLlqiKq9hUtTnTo6Ouz/kQ99v/B1zL3fMZ5BcVp1TfOgvbVOva+3NnzcQE80NskOikv0uydzDiQtD6tpbpF+QOg59ZeryT3R6wshgI5QinIWAXH23D5qnW2nf1TzkPV/w6WEr2QtCEZaD13SHOlIy6Z6++pVq413aVWv1DetVgN58I7e9oPiiU0qSuS7p+Mc6FpQLVvN7uof946tSPb6QgigIxQgaeCVmm41GEvToQQRb00y3lXYEOcAIYCO0AnDJFb/qtakj2enNsQ5QAigI3SCUpOw1vL2DthSX6umMWn6FiDhHCAE0BEKmNSvrFH0kfpfEecAIYCOEEIIIYCOEEIIAXSEEEIIAXSEEEIIAXSEEEIIAXSEEEIIoCOEEEIIoCOEEEIIoPtEWpxCezEf16HNI47z8/BOzPHNtYJvf/kG6BkEunbiOq5DF8Fxfh7eiTm+uVbw7S/fAB2gc+MRc3zjm5gDdATQufHwjm+uFXwDdIBOwiBh4B3feMc3QAfoJAx8E3N84x3fAB2gc+MRc3zjm5gDdATQufGIOb65VvAN0AE6Fy8JA+/4xju+ATpAJ2Hgm5jjG+/4BugA/dTeeE+fPvXtQbLDN97xDdCjaGNjw6yurtoApKKDgwP7XomWAXR/Av2HH37w3QHQ8c39iW+AHkFfv341RUVFprCw0Fy8eNGUlpaapaWlqK8vKCgISa4lJSVu2fDwsMnNzTXFxcWmsrLSbG5uxlUG0AE6QMc39ye+AXqKWl9fNzMzM+7jpqYm09zcHBPoer1q2zoEW2l7e9tkZ2ebxcVF+7ilpcW0t7cfWQbQATpAxzf3J74BegZ069YtmzBjAX12dvbQ82NjY6aiosJ9PD09bWvjR5UBdIAO0PHN/YlvgJ5GTU1Nmdu3b5uGhgaztbUVE+h6TVtbmxkfH3efHxgYMHV1de5j1cazsrKOLAPoAB2g45v7E98APY1SH7dAXVVVFfPL9PT0mMHBQdPd3W3y8/NNX1+ffV5JVs31jrSfrJLv3t5ezDKvvEk7XDoxx3Uc9+f51bufgX5aY45v7k98+8P3qWhyV7Ksr6+P+0eA05SuWrj3/4XX0KOVUUOnhk4NHd/cn/imhp4BjY6OmvLy8rheOzExYUfFS2p+9/aTqwnf6SePVQbQATpAxzf3J74BehqkEevz8/P27/39fVNbW2taW1vd8v7+ftPZ2Wn/Xl5edgfECbKNjY3uiPidnR07kn1hYcE+1vPOSPZYZQAdoAN0fHN/4hugp0GTk5N2frjmoOfl5ZmampqQQXFlZWVmaGjI/j03N2f7zTVnXYPjqqurD801z8nJsXPTVcvXlLh4ygA6QAfo+Ob+xDdAT9PSqlpgJnyxF60cJ9jv7u6GDE7T89EWhlEtf21tLeEygA7QATq+uT/xDdAzpJGREdPR0cFa7gAdoJPs8E3MAXqQga5auLd2DtABOkAn2eGbmAN0dlsD6AAdoOOb+xPfAB2gc/ECdICOb+5PfAN0gE7CAOhcL/jGO74BOkAH6ACdZIdvYg7QATpAB+gAnSTN/UnMAToC6AAdoOOb+xPfAB2gkzAAOtcLvvGOb4AO0I/nEKT8eAB0kh2+8Q7QATpAD3htGKCT7PCNd4AO0AE6QAfoJGl8E3OAjgA6QAfo+Abo+AboAB2gA3SSHb7xjm+ADtABuv+B7seDJI1vYg7QEUAH6AH3BdDxTcwBOgLoAB2gk6QBOr4BOkAH6AAdoJOk8Y5vgA7QATpAB+j4xju+TxfQNzY2zOrqqg3AUTo4OLCvT2fZcQHdrwOqADpAJ0kDdHwD9JT09etXU1RUZAoLC83FixdNaWmpWVpaivr64eFhk5uba4qLi01lZaXZ3NxMuey4gQ6kADpABy54x/epA/r6+rqZmZlxHzc1NZnm5uaIr93e3jbZ2dlmcXHRPm5paTHt7e0plQF0gA7QSdIAHd8APQO6deuWTU6RNDY2ZioqKtzH09PTtsadShlAB+gAnSQN0PEN0NOoqakpc/v2bdPQ0GC2trYivmZgYMDU1dW5j1XjzsrKSqkMoAN0gE6SBuj4BuhplPq4BfOqqqqoX0ZJS03yjlZWVmwy29vbS7rMK2+CDJdOTDoOIAXQM+UpXdfocR3pvK/wTcxPm+9T0eSuxFRfXx+1hu4tC6+FJ1NGDR2gU0On1kUNHd/U0DOg0dFRU15eHrFsfHw8pC9czfROX3iyZQAdoAN0kjRAxzdAT4M0wn1+ft7+vb+/b2pra01ra6tb3t/fbzo7O+3fOzs7drT6wsKCfazR8M5o9WTLADpAB+gkaYCOb4CeBk1OTtr54ZqDnpeXZ2pqakIGxZWVlZmhoaGQvvacnBxTUlJia/Ka9pZqGUAH6ACdJA3Q8Q3Q07S0qhaYCV/sRSvHCfa7u7shz6smv7a2FvG9ki0D6AAdoJOkATq+AXqGNDIyYjo6Ok7VWu5ACqADdOCCd3yfOaCrxh5eOwfoAB1fAB3fxBygs9saQAfoAJ0kDdDxDdABOkAH6ACdJA3Q8Q3QATpAB+gAHbjgHd8AHaADdHwBdHwTc4AO0AE6QAfoJGmAjm+ADtABOp4AOkkaoOMboAN0gA7QATpJGu/4BugAHaDjC6Djm5gDdIAO0AE6QCdJA3RiDtARQMcTQCdJA3R8A3SADtABOkAnSeMd3wAdoAN0YgXQ8U3MATpAB+gAHaCTpPFNzAE6Auh4AugkaYCOb4AO0AE6QAfoJGm84xugA3QgRawAOr7xDtABOkAH6ACdJI1vYg7QT0IbGxtmd3c35fc5ODiw75VoGUAH6ACdJA3Q8Q3QU9Dq6qopKioyhYWF5tKlS6axsdHs7e1FfX1BQUFIIispKXHLhoeHTW5urikuLjaVlZVmc3MzrjKADtABOkkaoOMboKehZj4zM+PWoKuqqsyrV69iAl2v12t1CLbS9va2yc7ONouLi/ZxS0uLaW9vP7IMoAN0gE6SBuj4BugZkGDb3NwcE+izs7OHnh8bGzMVFRXu4+npaVsbP6oMoAN0gE6SBuj4BugZUFlZmRkcHIwJ9IaGBtPW1mbGx8fd5wcGBkxdXZ37WLXxrKysI8u88ibIcOnEpOMAUgA9U57SdY0e15HO+wrfxPy0+Q480Ht7e22feKzBcT09PRb43d3dJj8/3/T19dnnldCamprc162srNhEp/74WGXU0AE6NXRqXdTQ8U0N/e9fH+nQhw8fTF5envny5Uvc/0cD3ZymdNXC6+vro9bQo5UBdIAO0EnSAB3fAP277t69a+7cuWOmpqaShvv8/Lwd5f7+/fuE/t/ExIQpLS21f6v53dtPLj9OP3msMoAO0AE6SRqg4xugf9etW7fcpKJpZ11dXe5o8ni0tLRkp60JspHU399vOjs77d/Ly8vugDhBVlPcnAF0Ozs7diT7wsKCfaznnZHsscoAOkAH6CRpgI5vgP537fenn34y586dC0kw165ds33dW1tbMf//6OhoxATlTEfTILmhoSH799zcnO03V21eg+Oqq6sPzTXPycmx/fDl5eVmfX09rjKADtABOkkaoOObUe5/69u3b+bly5d2BLo30aivWjXpt2/fJrXojBaD8Q6SU4D0fLSFYfb3983a2lrCZQAdoAN0kjRAxzdA/65Pnz6ZBw8e2ObzSAnH24cdr0ZGRkxHRwdruQN0fAF0fBNzgJ5poD9//vwQxFUrV01dzfGaiqZm82SArlp4OtZ3B+gAHaCTpAE6MQfoCQyKU9+05oVHag5PZKAcu60BKYAO0PGNd4B+zEC/d++enbb25s2bkP50ATyRgWcAHUgBdICOb7zj+wSB/vjxY5tQtIKbtzZ+4cKFkJ3QADpAB+iZ9eTXgySNd3wHBOjXr1+308G081n4gjNKNF+/fgXoAB2gn+FYkaTxju+AAF0D3rRkazSgB7nvHKDjiVgBdICO7zMD9J9//tltcnc2O9EUNjW5J7IBCkAHCAAdoAMXvOP7BIH+4sUL9+Y9f/68uXjxovtYsKcPHaADdIBOksY7vgMAdMFOW5OG38ja/OQ0NLcDdDwRK4AO0PF9plaK07S1J0+e2E1PtNjM9va2OS0C6HgiVgAdoOP7zABd0NO88/ADoAN0gA7QSdJ4x3cAgK6NUrRanDZRiXRDA3SADtABOkka7/gOANC1V3msGxqgA3SADtBJ0njHdwCArk1XdONq3rk2Y1FfuvcA6AAdoAN0kjTe8R0AoGt7U924y8vL5rQKoOOJWAF0gI7vUw/0qakpu13q/fv3zeTk5KEDoAN0gA7QSdJ4x3fAtk+lDx2gA3Q8AXS84xugA3SAANABOnDBO75PCug7Oztma2sr6hGvNjY2zO7ublyv1UYwen06ywA6kCJWAB2g4/vMLyzz8uVLU1lZabKzs01zc7P5+PGjKS0ttSPf45nHXlRUZAoLC82lS5dMY2NjzA1dhoeH7Zx3LS2rz9zc3Ey5DKADKWIF0AE6vs880F+/fh1yAwvoznQ2DZbb398/smY+MzPj1qCrqqrMq1evIr5Wy8nqR4OzRnxLS4tdajaVMoAOpIgVQAfo+Abo31VTU2POnTtnR7mrhu0A3ZnOlugGLYKt8x7hGhsbsz8UHE1PT9sadyplAB1IESuADtDxDdC/S2C8efOm/bu8vNyFcVtbm72hl5aWEnq/srIyMzg4GLFsYGDA1NXVuY/1Y0GtAKmUeRVrMJ9OTDoOIAXQz1qsot0L6byvjvMIqm9iHizfJwL06upq2/+tAW0O0NWMrud0Q6sZPV719vaakpKSqIPjlBy0VaujlZUV+xnqc0+2jBo6kCJW1NCpoeObGvp3PXv2zN646qO+cOGCyc/Pt3/rufr6+rjf58OHDyYvL898+fIl6mtU0/a+Z3gtPJkygA6kiBVAB+j4Buh/w662tvbQjayR6/E2t8/Pz9sa/fv372O+TmvFe/vCtUqd0xeebBlAB1LECqADdHwDdI+0zGtXV5ftOx8aGrLz0+ORoC/4C7KR1N/fb3d0c+a8q/a/sLBgH6t53xmtnmwZQAdSxAqgA3R8A/S/p5qpLzracZRGR0cjJgJB1Bkkpx8I3vnkOTk5tq9dffbr6+splwF0IEWsADpAxzdLv2Zw6VctOqPFYMIHyWlu+9raWsT/k2wZQAdSxAqgA3R8A/QMAX1kZMTOZ2ctdyCFL4AOXAAjQM8w0LXM6++//x5yvHjxwi42o/70VKTlWeNd3x2gAyliBdCBC2AE6BnQ1atXExpJzm5rAAGgA3Tggnd8nyDQNXL8zz//dA8tq/r8+XM7J103tBZxAegAHaADdJI03vEd4D50TUdLZgk7gA4QADpABy54B+g+Afq1a9fsgi40uQN0gA7QSdJ4x3cAgK5125eXl0OOra0tc5oE0PFErAA6QMf3mR0UB9ABOkAnVgAd7/gOENC1f7n2QT/q+PnnnwE6QAfoAB244B3fQV1Yxjm8m6MAdCAF0M9OrPx6AHR8A/QwaeMU3biPHz92b5QnT56Y8+fPm7q6Ovc5LTYD0AE6QCdWfm85AOj4PrNAv3nzpp1zrk1avFITu6atOZus0IcO0IEUsQLogBHfPga6VoTTDdLb2+tCXZuqaGczPe9sWQrQATqQIlYAHTDi28dAb21tdW8S1dRVK3ceaz33ePdFB+gkXiBFrAA6YMT3CQJ9e3vbLiITfsNkZWWZ/v5+pq0BdCBFrAA6YMR3UOahy/Qff/xhHj16ZB4+fGjevn3ri13SADqQIlb4AugAHaAnoJcvX5rKykqTnZ1tmpub7ZaqpaWl5u7duwAdoAMpYgXQASO+gwD0169fh9woArqkeedqdt/f3wfoAB1IESuADhjx7Xeg19TU2MFv9+/ftyvCOUDv6OiwN87i4iJAB+hAilgBdMCIb78Dvbi42M5Fl8rLy12gt7W12RtnaWkp7vcKn8uejPQe2jAm0TKADqSIFSvYBXX1OsAI0NMC9OrqalNYWGgHwTlAFzT1nG6ceCGtL6Ca/lFN9AUFBSE3ZklJiVs2PDxscnNz7Y8M9elvbm7GVQbQgRSxwleQWw0AI0BPC9CfPXtmL0YNiNM89Pz8fPu3nquvr4/rPdrb221/u/5PPECfmZmxPxR0OCvRafqcPtdp4temMXrfo8oAOpAiVvgC6AAdoP8Nu9ra2kMXpxaYSaS5XTXmeGr0Avrs7Oyh58fGxkI2gJmenra18aPKADqQIlb4AugAHaB/l5rav337ZsbHx01XV5ftOx8aGkp4hbhEgN7Q0GA/R5/paGBgwG4G40i1cdX6jyrzyntzRZprn46DBAcMiBW+onlKV56JdqQzlx3ncRZ9nwjQne1TBfNUFC/Qe3p6zODgoOnu7rbN+319ffZ53QxNTU3u61ZWVuz77e3txSyjhg6kiBW+qKFTQ6eG/l3aFlUXo7ZRPQ6ge6WBbk5Tumrh3j778Bp6tDKADqSIFb4AOkAH6N/16dMnO/9cI9wnJycPHZkE+sTEhF2RTlLzu7effGpqyu0nj1UG0IEUscIXQAfoAN3T5B7tSBXo2uDFqf0vLy+7A+IE2cbGRnfeu/rsNZLd2a5Vzzsj2WOVAXQgRazwBdABOkBPE9A1jcyZt67a/pMnT9yysrIyO8hOmpubs/3meq0Gx2kOfPhcc+3DrrnpajFYX1+PqwygAylihS+ADtDPJNAFXEFRMH3//r0ZHR01W1tbEY9UtLq6aheD8e7cpgDp+WgLw2ge+9raWsJlAB1IESt8AXSAfuaArhHjugC1YItq6FrhLRMaGRmxa8KzljuQwhexAuiAEaBnAOja91wXoGCuwWax1kZOdeS7H/ZVB+h4Ilb4AugA/VQC/fPnz3FfpKdBAB1PxApfAB2gn9pBcVpOVU3vWr9dF6MGqkU6ADpABwbECl8AHd8BGOWu6V+XL182p1kAHU/ECl8AHaCfeqCfBQF0PBErfAF0gA7QATpABwbECl8AHd8AHaADKXwRK4AOGAE6QAfoQIpY4QugA3SAjgA6nogVvgA6QAfoAB2gAwNihS+Ajm+ADtBJcMSKWAF0wAjQATpAB1LECl8AHaADdATQ8USs8AXQATpAB+gAHRgQK3wBdHwDdIBOggMGxAqgA0aADtABOpAiVvgC6IARoCOAjidihS+ADtABOkAH6MAAT/gC6Pg+60A/ODiI+3UbGxtpLQPoQIpY4QugA3SAngbpC5w7d87s7+/HfN3w8LDJzc01xcXFprKy0mxubqZcBtCBFLHCF0AH6AA9DWpvbzdZWVn2oo4F9O3tbZOdnW0WFxft45aWFvt/UykD6ECKWOELoAN0gJ5GqcasizpWs/vY2JipqKhwH09PT9sadyplAB1IESt8AXSADtCPGegDAwOmrq7Ofawat2r2qZR55b25wqUTk46DBAcMiBW+onlKV56JdqQzlx3ncRZ9n3qg64JvampyH6+srNj/s7e3l3QZNXQgRazwRQ2dGjo19BOoodfX10ethSdTBtCBFLHCl188+fUA6AA97UAfHx8P6Qufmppy+8KTLQPoQIpY4QtPJ9tyANDPCND7+/tNZ2en/XtnZ8eOVl9YWLCPm5ub3dHqyZYBdBIcscIXngA6QE+TNI2ssLDQXjyXLl0yT548ccvKysrM0NBQyHzynJwcU1JSYsrLy836+nrKZQCdBEes8IUngA7QM6jV1VW7GMzu7m7I85qrvra2FvH/JFsG0ElwxApfeALoAD1DGhkZMR0dHazlToLDF7HiHAJ0gB5koKtfPbx2DtBJcPgiVpxDgA7Q2W0NoJPgiBW+OIcAHaADdIBOgiNW+ALoAB2gA3SADgyIFb4AOkAH6ACdBIcvYsU5BOgAHaADdBIcscIX5xCgA3SADtDxRKzwBdABOkAH6AAdGBArfAF0gA7QAToJDl/EinMI0AE6QAfoJDhihS/OIUAH6Aig44lY4QugA3SADtABOjAgVvgC6AAdoAN0EhyxIlacQ4AO0AE6QCfBESt8cQ4BOkBHAB1PxApfxAqgA3SADtCBAbHCF0AH6AAdoJNMiBWx4hwCdIB+hoF+cHBgNjY2Ei4D6CQ4YoUvPAF0gH6MKigoCLnYSkpK3LLh4WGTm5triouLTWVlpdnc3IyrDKCT4IgVvvAE0AH6CQB9ZmbG1rZ1CLbS9va2yc7ONouLi/ZxS0uLaW9vP7IMoJPgiBW+8ATQAfoJAX12dvbQ82NjY6aiosJ9PD09bWvjR5UBdBIcscIXngA6QD8hoDc0NJi2tjYzPj7uPj8wMGDq6urcx6qNZ2VlHVkG0ElwxApfeALoAP0E1NPTYwYHB013d7fJz883fX199nlddE1NTe7rVlZW7MW4t7cXs8wr70UcLp2YdBwkOGBArPAVtFilK/8le6QzBwfF95kb5a6Bbk5Tumrh9fX1UWvo0cqooZPgiBW+8EQNnRr6CWtiYsKUlpbav9X87u0nn5qacvvJY5UBdBIcscIXngA6QD9mLS8vuwPiBNnGxkbT3NxsH+/s7NiR7AsLC/axnndGsscqA+gkOGKFLzwBdIB+zJqbm7P95oWFhXZwXHV19aG55jk5OXZuenl5uVlfX4+rDKCT4IgVvvAE0AH6MUsBWl1djbowzP7+vllbW0u4DKCT4IgVvvAE0AE6a7kDdGCAJ3wBdIAO0AE6yQQYECvOIUAH6AAdoJPgiBWx4hwCdICOADoJjljhC08AHaADdIAODPCEL4AO0AE6QCeZAANihS+ADtABOkAnweGLWHEOM+7LjwdAB+gAnQRHrPDFOTwFsQLoAB2gk+CIFb44hwAdoAN0gE6CI1b4IlYAHaADdIBOgiNW+CJWAB2gA3QSHL6IFecQXwAdoAN0EhyxwhfnEKADdIAO0PFErPBFrAA6QAfoAJ0ER6zwRawAOkAH6CQ4fBErziG+ADpAB+gkOGKFL84hQAfoCKDjiVjhi1gBdIAO0AE6CY5Y4YtYAXSAnl4dHByYjY0NgE6CI1b4whNAB+hB1fDwsMnNzTXFxcWmsrLSbG5uAnQSHLHCF54AOkAPkra3t012drZZXFy0j1taWkx7eztAJ8ERK3zhCaAD9CBpbGzMVFRUuI+np6dtTR2gk+CIFb7wFMxYBWGfdoCeAQ0MDJi6ujr3sWrqWVlZAJ0ER6zwhSdilbGWA4CeASnITU1N7uOVlRUb/L29vZDXeU+MV9XV1eaf//xnWg4//7Lk4ODg4Ejt8Ob7f/3rX0mzAqDHqKHX19cnXUNHCCGE/KwzA/Tx8fGQPvSpqamE+tARQgghgO4D7ezs2FHuCwsL9nFzc3NCo9wRQgghgO4TaR56Tk6OKSkpMeXl5WZ9fd23XsP78IOkoHon5vjmWsF3kH2fuZXi9vf3zdraGhcB3ok5vrlW8A3QERcvNx7e8c21gm+AjhBCCCGAjhBCCCGAjhBCCAF0hBBCCAF0FFXao313dzdiWbJ7uB+X79XVVbtecZB8H6Wgeve7b10nX79+tT65Vo4n3tG2ifazby3JHc2bH32HX8/xeM3E9wDoJyzBsKioyBQWFppLly6ZxsbGkPXlU9nDPZNSUnZ8X7x40ZSWlpqlpSXf+w7XmzdvzLlz58zs7GwgvBcUFITsN6A1FYLgW8lLWxZrHQhd5y9evAiE76GhoYgbcDhTX/3s/dmzZ3Z1zBs3bpjr16+b+fn5QMT80aNH5sKFCzanyLfXmx99axMV5RBNifYqltdMfQ+A7oMa7szMjJv0qqqqzKtXr+zjVPdwz6S0KI/jW9LGN1p9z+++vVKCu3LlioWMA3S/exfQFXddKzq0C2AQfN+9e9c0NDS4O0g5LTp+9y2fTqx16JpRIlZrmt/vz7y8PLtCpgP3X375xfcxf/funfXt1Fxv375tWltbfetbn689QfQjzwv0WF4z+T0Aus+kk+uAMdU93I9Tt27dsrsMBcW3EoZgPjc3Z1sYHKD73buA7m1NcORn36rNKuk5CSwoviPpzp07pqury/feP3z44P7wkLSXhWrqfvf98OHDkF0x379/73rzq2/VrgV0b7N7LK+Z/B4A3WcqKyszg4OD9u9U93A/DmmTG/2KVu1ra2srEL71S1pNeUpykhfofvcuoCvWbW1trn+/+5ZPNbM/ePDA1NTU2BqX08QYhGvckVoX1JoThOtcLTc3b9604FC3kuL+9u1b3/tWpUDdjo7UjafmbLWU+NV3JKDH8prJ7wHQfaTe3l7bJ+r8qo53D/eTlPqCBBh1FTjNqX73rVaQ+/fvm2/fvtlDkNQPEyVBv3vv6emxP/i6u7tNfn6+6evr833M+/v77XgLdSWpxqVkJtgE5Rp3pB+unZ2dIfDxs/d///vfdstoVRIuX75sx7343feXL19s/7mua/0QuXfvnoWdgO5X35GAHstrJr8HQPeJ1ESmviNd0N5feUHZw10XqePV775VA9BgPudQDUBw/PjxY6Birh9TTtOdn30L6F5vTgJT/25Q4q0fq+r39I5K9rN3NeMK4gKhDrXoaEOqINyf8i7gqRuvo6PDbY72q+9oNfRoXjP5PQC6D6SBNgKLai9eBWkP99HRUTdhBG3veW+Te5C8T0xM2JHAfvctn8614QW6mq6DEm/VztWqE5T788mTJ4earp0fUUG6xjWe6LfffvN1vCMBPZbXTH4PgH7C0o2m5kid1HD5eQ93jbR2psGoT7q2ttYdjRq0vee9QPez9+XlZdenugeUsJ0BlH72rW4NDdD69OmTfaxug6tXrwbmWlHtXM3A4dst+9n769evbX+/Yi89f/7chYbfY+7MLFAlQd1hGrjqZ9+RgB7Laya/B0D3Qc020jxXZzqSX/dwn5yctElaMFRXgQbdOIOF/Oz7KKD72bsSm7oG1JqjRFddXX1obqtfYy7AyLu8CSxOkg7CtaLauZqsI8mv3gVF1WzlTeNbNGbB+UHl95hrXrauFVV0nIF8fvWt8Ti6H5WzNfBTLSPxeM3U9wDoAZBf93DXjw4NtIm2KEJQ9p4Pknclai1GFMSYy5uul0irCnKtZK51JBos/Opb4xRiLbQSpGslltdMfA+AjhBCCJ0CAXSEEEIIoCOEEEIIoCOEEEIIoCOEEEIIoCOEEEIAHSGEEEIAHSGEEEIAHSGEEEIAHSGEEALoCKGzLC3d2tDQYNeh1nrUWstaa/lrVzVH2khGW0RqTW7tLKV1xbX2vLbEdKT9nx8+fGiuXLliN6vQv9paMtLSsAghgI4QygDQtRmFtm7Vxh/apEePta+8NrkRqLVRhbPRkDaS0X7PzgYWjm7cuGGfKysrs6A/f/68fTw0NESQEQLoCKHj0OLiovu3NpjQ9qKCcW9vr91JyoH5yMiI/QHw9OnTEKBrT2g91o8B/QCQOjs77XPevdMRQgAdIZRBvXv3zu7HriZ177a/jx49Mvfv37d/a1tdp/k8HOhdXV32sWrlarrXoZq8ntOPA4QQQEcIHQPMHYCrqby/v9/uKR8OdDWlOwoHent7u9scr73Gww+EEEBHCGVYHR0dFsaXL192nxO8HaBrYJsDfMF/Z2fH3LlzJwTo6ifXYw2GC9+v+8OHDwQZIYCOEMq0Xr586Q6CU2387t27IU3u3759s6PfvU3xzuEAXZAvLi62z2mUfGtrq/2hoP5zDbZDCAF0hFCGpUFsdXV1FuhOs3tRUZELdOnLly922pqmot2+fdseKr969ar7PktLS/Y1XuBrNLx+ICCEADpC6JikpvLw5nJHajbf3d21f6vGXllZaYH966+/HnrtwcGBhbtGzu/v7xNYhAA6QsgvUk1cNXgNlnNq8hrRPjc3R3AQAugIoaBI09J+/PFH2x+uleI0KO7z588EBiGAjhBCCCGAjhBCCAF0hBBCCAF0hBBCCAF0hBBCCAF0hBBCCKAjhBBCKBj6P2QkVLvBBQ+gAAAAAElFTkSuQmCC\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x5d95c112 \"org.jfree.chart.JFreeChart@5d95c112\"], :opts nil}"}
;; <=

;; **
;;; Histograms are generally a very good way to see the shape of a single 
;;; distribution, but that shape can change depending on how the data is split 
;;; between the different bins.  You can control the number of bins by adding an 
;;; argument to the command. In the next lines, we first make a default 
;;; histogram of `bmi` and then one with 50 breaks.
;; **

;; @@
(chart-view
  (c/histogram (i/sel cdc-bmi :cols :bmi)
               :title "Histogram of BMI"
               :x-label "BMI"
               :y-label "frequency"))

(chart-view
  (c/histogram (i/sel cdc-bmi :cols :bmi)
               :title "Histogram of BMI"
               :x-label "BMI"
               :y-label "frequency"
               :nbins 50))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x4fc7f1bb \"org.jfree.chart.JFreeChart@4fc7f1bb\"], :opts nil}"}
;; <=

;; **
;;; How do these two histograms compare?
;; **

;; **
;;; At this point, we've done a good first pass at analyzing the information in the 
;;; BRFSS questionnaire.  We've found an interesting association between smoking and
;;; gender, and we can say something about the relationship between people's 
;;; assessment of their general health and their own BMI.  We've also picked up 
;;; essential computing tools -- summary statistics, subsetting, and plots -- that 
;;; will serve us well throughout this course.
;; **

;; **
;;; * * *
;;; 
;;; ## On Your Own
;;; 
;;; -   Make a scatterplot of weight versus desired weight. Describe the 
;;;     relationship between these two variables.
;;; 
;;; -   Let's consider a new variable: the difference between desired weight 
;;;     (`wtdesire`) and current weight (`weight`). Create this new variable by 
;;;     subtracting the two columns in the dataset and assigning them to a new 
;;;     column called `wdiff`.
;;; 
;;; -   If an observation `wdiff` is 0, what does 
;;;     this mean about the person's weight and desired weight. What if `wdiff` is 
;;;     positive or negative?
;;; 
;;; -   Describe the distribution of `wdiff` in terms of its center, shape, and 
;;;     spread, including any plots you use. What does this tell us about how people 
;;;     feel about their current weight?
;;; 
;;; -   Using numerical summaries and a side-by-side box plot, determine if men tend
;;;     to view their weight differently than women.
;;; 
;;; -   Now it's time to get creative. Find the mean and standard deviation of 
;;;     `weight` and determine what proportion of the weights are within one 
;;;     standard deviation of the mean.
;;; 
;; **
