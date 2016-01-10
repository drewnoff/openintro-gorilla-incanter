;; gorilla-repl.fileformat = 1

;; **
;;; # Inference for numerical data
;;; 
;;; Original lab is available [here](http://htmlpreview.github.io/?https://github.com/andrewpbray/oiLabs-base-R/blob/master/inf_for_numerical_data/inf_for_numerical_data.html).
;;; You can download the data for this lab from [here](https://www.openintro.org/stat/data/?data=nc). 
;; **

;; @@
(ns openintro.inf-for-numerical-data)

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
;;; ## North Carolina births
;;; 
;;; In 2004, the state of North Carolina released a large data set containing 
;;; information on births recorded in this state. This data set is useful to 
;;; researchers studying the relation between habits and practices of expectant 
;;; mothers and the birth of their children. We will work with a random sample of 
;;; observations from this data set.
;; **

;; **
;;; ## Exploratory analysis
;; **

;; **
;;; Load the `nc` data set into our workspace.
;; **

;; @@
(def nc (iio/read-dataset "../data/nc.csv" :header true))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.inf-for-numerical-data/nc</span>","value":"#'openintro.inf-for-numerical-data/nc"}
;; <=

;; **
;;; We have observations on 13 different variables, some categorical and some 
;;; numerical. The meaning of each variable is as follows.
;;; 
;;; variable         | description
;;; ---------------- | -----------
;;; `fage`           | father's age in years.
;;; `mage`           | mother's age in years.
;;; `mature`         | maturity status of mother.
;;; `weeks`          | length of pregnancy in weeks.
;;; `premie`         | whether the birth was classified as premature (premie) or full-term.
;;; `visits`         | number of hospital visits during pregnancy.
;;; `marital`        | whether mother is `married` or `not married` at birth.
;;; `gained`         | weight gained by mother during pregnancy in pounds.
;;; `weight`         | weight of the baby at birth in pounds.
;;; `lowbirthweight` | whether baby was classified as low birthweight (`low`) or not (`not low`).
;;; `gender`         | gender of the baby, `female` or `male`.
;;; `habit`          | status of the mother as a `nonsmoker` or a `smoker`.
;;; `whitemom`       | whether mom is `white` or `not white`.
;;; 
;;; *1.  What are the cases in this data set? How many cases are there in our sample?*
;; **

;; **
;;; As a first step in the analysis, we should consider summaries of the data. For categorical columns this
;;; can be done using the `category-col-summarizer` function and for numerical columns we can use `quantile` function:
;; **

;; @@
(doall
  (map println
       (map #(s/category-col-summarizer % nc)
            [:mature :premie :marital :gender :habit :whitemom])))

(println (clojure.string/join "\t" ["col" "Min." "1st Qu." "Median" "3rd Qu" "Max."]))
(doall
  (map #(println (clojure.string/join "\t" %))
       (map #(conj (s/quantile (filter (partial not= "NA") (i/sel nc :cols %))) %)
            [:fage :mage :weeks :visits :gained :weight])))
;; @@
;; ->
;;; {:col :mature, :count 0, :is-numeric false, younger mom 867, mature mom 133}
;;; {:col :premie, :count 0, :is-numeric false, full term 846, premie 152, NA 2}
;;; {:col :marital, :count 0, :is-numeric false, not married 613, married 386, NA 1}
;;; {:col :gender, :count 0, :is-numeric false, female 503, male 497}
;;; {:col :habit, :count 0, :is-numeric false, nonsmoker 873, smoker 126, NA 1}
;;; {:col :whitemom, :count 0, :is-numeric false, white 714, not white 284, NA 2}
;;; col	Min.	1st Qu.	Median	3rd Qu	Max.
;;; :fage	14.0	25.0	30.0	35.0	55.0
;;; :mage	13.0	22.0	27.0	32.0	50.0
;;; :weeks	20.0	37.0	39.0	40.0	45.0
;;; :visits	0.0	10.0	12.0	15.0	30.0
;;; :gained	0.0	20.0	30.0	38.0	85.0
;;; :weight	1.0	6.38	7.31	8.06	11.75
;;; 
;; <-
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"(nil nil nil nil nil nil)"}
;; <=

;; **
;;; For numerical variables, are there outliers? If you 
;;; aren't sure or want to take a closer look at the data, make a graph.
;;; 
;;; Consider the possible relationship between a mother's smoking habit and the 
;;; weight of her baby. Plotting the data is a useful first step because it helps 
;;; us quickly visualize trends, identify strong associations, and develop research
;;; questions.
;;; 
;;; *2.  Make a side-by-side boxplot of `habit` and `weight`. What does the plot 
;;; highlight about the relationship between these two variables?*
;;; 
;;; The box plots show how the medians of the two distributions compare, but we can
;;; also compare the means of the distributions using the following function to 
;;; split the `weight` variable into the `habit` groups, then take the mean of each
;;; using the `mean` function.
;; **

;; @@
(print (i/$rollup s/mean :weight [:habit] nc))
;; @@
;; ->
;;; 
;;; |    :habit |           :weight |
;;; |-----------+-------------------|
;;; | nonsmoker | 7.144272623138631 |
;;; |    smoker | 6.828730158730158 |
;;; |        NA |              3.63 |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; There is an observed difference, but is this difference statistically 
;;; significant? In order to answer this question we will conduct a hypothesis test.
;; **

;; **
;;; ## Inference
;; **

;; **
;;; *3.  Check if the conditions necessary for inference are satisfied. Note that 
;;; you will need to obtain sample sizes to check the conditions. You can compute 
;;; the group size using the same `$rollup` function above but replacing `mean` with 
;;; `count`.*
;;; 
;;; *4.  Write the hypotheses for testing if the average weights of babies born to 
;;; smoking and non-smoking mothers are different.*
;;; 
;;; Next, we introduce a new function, `t-test`, that we will use for conducting
;;; hypothesis tests and constructing confidence intervals. 
;; **

;; @@
(s/t-test (i/sel (i/$where {:habit "nonsmoker"} nc) :cols :weight)
          :y (i/sel (i/$where {:habit "smoker"} nc) :cols :weight)
          :paired false
          :conf-lavel 0.95
          :alternative :two-sided)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:p-value</span>","value":":p-value"},{"type":"html","content":"<span class='clj-double'>0.019450556443720846</span>","value":"0.019450556443720846"}],"value":"[:p-value 0.019450556443720846]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:df</span>","value":":df"},{"type":"html","content":"<span class='clj-double'>171.32465666242578</span>","value":"171.32465666242578"}],"value":"[:df 171.32465666242578]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:n2</span>","value":":n2"},{"type":"html","content":"<span class='clj-unkown'>126</span>","value":"126"}],"value":"[:n2 126]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:x-mean</span>","value":":x-mean"},{"type":"html","content":"<span class='clj-double'>7.144272623138631</span>","value":"7.144272623138631"}],"value":"[:x-mean 7.144272623138631]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:y-mean</span>","value":":y-mean"},{"type":"html","content":"<span class='clj-double'>6.828730158730158</span>","value":"6.828730158730158"}],"value":"[:y-mean 6.828730158730158]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:x-var</span>","value":":x-var"},{"type":"html","content":"<span class='clj-double'>2.306390783389556</span>","value":"2.306390783389556"}],"value":"[:x-var 2.306390783389556]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:conf-int</span>","value":":conf-int"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>0.05150536718699117</span>","value":"0.05150536718699117"},{"type":"html","content":"<span class='clj-double'>0.5795795616299553</span>","value":"0.5795795616299553"}],"value":"[0.05150536718699117 0.5795795616299553]"}],"value":"[:conf-int [0.05150536718699117 0.5795795616299553]]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:y-var</span>","value":":y-var"},{"type":"html","content":"<span class='clj-double'>1.9214943746031747</span>","value":"1.9214943746031747"}],"value":"[:y-var 1.9214943746031747]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:t-stat</span>","value":":t-stat"},{"type":"html","content":"<span class='clj-double'>2.359010944933727</span>","value":"2.359010944933727"}],"value":"[:t-stat 2.359010944933727]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:n1</span>","value":":n1"},{"type":"html","content":"<span class='clj-unkown'>873</span>","value":"873"}],"value":"[:n1 873]"}],"value":"{:p-value 0.019450556443720846, :df 171.32465666242578, :n2 126, :x-mean 7.144272623138631, :y-mean 6.828730158730158, :x-var 2.306390783389556, :conf-int [0.05150536718699117 0.5795795616299553], :y-var 1.9214943746031747, :t-stat 2.359010944933727, :n1 873}"}
;; <=

;; **
;;; We supply two sets of data (one as first arg and second as `:y` option)
;;; and set up `:paired` option to `false` because
;;; we are interesting in hypothesis tests based on a difference in means.
;;; `:conf-level` option corresponds to the returned confidence interval.
;;; The `:alternative` hypothesis can be `:less`, `:greater`, or `two-sided`.
;; **

;; **
;;; For one sample t-tests one can use `t-test` function without `:y` option and with appropriate `:mu` option (default 0).
;; **

;; **
;;; As an example of one sample t-test
;;; we can calculate a 95% confidence interval for the average length of pregnancies 
;;; (`:weeks`).
;; **

;; @@
(def weeks
  (filter (partial not= "NA") (i/sel nc :cols :weeks)))

((s/t-test weeks
          :mu (s/mean weeks)
          :conf-lavel 0.90
          :alternative :two-sided) :conf-int)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>38.15279117659766</span>","value":"38.15279117659766"},{"type":"html","content":"<span class='clj-double'>38.516547500757056</span>","value":"38.516547500757056"}],"value":"[38.15279117659766 38.516547500757056]"}
;; <=

;; **
;;; * * *
;;; 
;;; ## On your own
;;; -   Interpret obtained 95% confidence interval for the average length of pregnancies.
;;; 
;;; -   Calculate a new confidence interval for the average length of pregnancies at the 90% 
;;; confidence level.
;;; 
;;; -   Conduct a hypothesis test evaluating whether the average weight gained by 
;;; younger mothers is different than the average weight gained by mature mothers.
;;; 
;;; -   Now, a non-inference task: Determine the age cutoff for younger and mature 
;;; mothers. Use a method of your choice, and explain how your method works.
;;; 
;;; -   Pick a pair of numerical and categorical variables and come up with a 
;;; research question evaluating the relationship between these variables. 
;;; Formulate the question in a way that it can be answered using a hypothesis test
;;; and/or a confidence interval. Answer your question using the `t-test` 
;;; function, report the statistical results, and also provide an explanation in 
;;; plain language.
;; **
