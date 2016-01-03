;; gorilla-repl.fileformat = 1

;; **
;;; # Probability
;;; 
;;; Original lab is available [here](http://htmlpreview.github.io/?https://github.com/andrewpbray/oiLabs-base-R/blob/master/probability/probability.html).
;;; You can download the data for this lab from [here](https://www.openintro.org/stat/data/?data=kobe). 
;; **

;; @@
(ns openintro.probability)

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
;;; ## Hot Hands
;;; 
;;; Basketball players who make several baskets in succession are described as 
;;; having a *hot hand*. Fans and players have long believed in the hot hand 
;;; phenomenon, which refutes the assumption that each shot is independent of the 
;;; next. However, a 1985 paper by Gilovich, Vallone, and Tversky collected evidence
;;; that contradicted this belief and showed that successive shots are independent 
;;; events ([http://psych.cornell.edu/sites/default/files/Gilo.Vallone.Tversky.pdf](http://psych.cornell.edu/sites/default/files/Gilo.Vallone.Tversky.pdf)). This paper started a great controversy that continues to this day, as you can 
;;; see by Googling *hot hand basketball*.
;;; 
;;; We do not expect to resolve this controversy today. However, in this lab we'll 
;;; apply one approach to answering questions like this. The goals for this lab are 
;;; to (1) think about the effects of independent and dependent events, (2) learn 
;;; how to simulate shooting streaks with Incanter, and (3) to compare a simulation to actual
;;; data in order to determine if the hot hand phenomenon appears to be real.
;; **

;; **
;;; ## Getting Started
;; **

;; **
;;; Our investigation will focus on the performance of one player: Kobe Bryant of 
;;; the Los Angeles Lakers. His performance against the Orlando Magic in the 2009 
;;; NBA finals earned him the title *Most Valuable Player* and many spectators 
;;; commented on how he appeared to show a hot hand. Let's load some data from those
;;; games and look at the first several rows.
;; **

;; @@
(def kobe (iio/read-dataset "../data/kobe.csv" :header true))
(print (i/head kobe))
;; @@
;; ->
;;; 
;;; | :vs | :game | :quarter | :time |                                            :description | :basket |
;;; |-----+-------+----------+-------+---------------------------------------------------------+---------|
;;; | ORL |     1 |        1 |  9:47 |                 Kobe Bryant makes 4-foot two point shot |       H |
;;; | ORL |     1 |        1 |  9:07 |                               Kobe Bryant misses jumper |       M |
;;; | ORL |     1 |        1 |  8:11 |                        Kobe Bryant misses 7-foot jumper |       M |
;;; | ORL |     1 |        1 |  7:41 | Kobe Bryant makes 16-foot jumper (Derek Fisher assists) |       H |
;;; | ORL |     1 |        1 |  7:03 |                         Kobe Bryant makes driving layup |       H |
;;; | ORL |     1 |        1 |  6:01 |                               Kobe Bryant misses jumper |       M |
;;; | ORL |     1 |        1 |  4:07 |                       Kobe Bryant misses 12-foot jumper |       M |
;;; | ORL |     1 |        1 |  0:52 |                       Kobe Bryant misses 19-foot jumper |       M |
;;; | ORL |     1 |        1 |  0:00 |                                Kobe Bryant misses layup |       M |
;;; | ORL |     1 |        2 |  6:35 |                                Kobe Bryant makes jumper |       H |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; In this data frame, every row records a shot taken by Kobe Bryant. If he hit the
;;; shot (made a basket), a hit, `H`, is recorded in the column named `basket`, 
;;; otherwise a miss, `M`, is recorded.
;; **

;; **
;;; Just looking at the string of hits and misses, it can be difficult to gauge 
;;; whether or not it seems like Kobe was shooting with a hot hand. One way we can 
;;; approach this is by considering the belief that hot hand shooters tend to go on 
;;; shooting streaks. For this lab, we define the length of a shooting streak to be 
;;; the *number of consecutive baskets made until a miss occurs*.
;;; 
;;; For example, in Game 1 Kobe had the following sequence of hits and misses from 
;;; his nine shot attempts in the first quarter:
;;; 
;;; $$ \textrm{H M | M | H H M | M | M | M} $$
;;; 
;;; To verify this use the following command:
;; **

;; @@
(i/sel kobe :cols :basket :rows (range 9))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;H&quot;</span>","value":"\"H\""},{"type":"html","content":"<span class='clj-string'>&quot;M&quot;</span>","value":"\"M\""},{"type":"html","content":"<span class='clj-string'>&quot;M&quot;</span>","value":"\"M\""},{"type":"html","content":"<span class='clj-string'>&quot;H&quot;</span>","value":"\"H\""},{"type":"html","content":"<span class='clj-string'>&quot;H&quot;</span>","value":"\"H\""},{"type":"html","content":"<span class='clj-string'>&quot;M&quot;</span>","value":"\"M\""},{"type":"html","content":"<span class='clj-string'>&quot;M&quot;</span>","value":"\"M\""},{"type":"html","content":"<span class='clj-string'>&quot;M&quot;</span>","value":"\"M\""},{"type":"html","content":"<span class='clj-string'>&quot;M&quot;</span>","value":"\"M\""}],"value":"(\"H\" \"M\" \"M\" \"H\" \"H\" \"M\" \"M\" \"M\" \"M\")"}
;; <=

;; **
;;; Within the nine shot attempts, there are six streaks, which are separated by a 
;;; "|" above. Their lengths are one, zero, two, zero, zero, zero (in order of 
;;; occurrence).
;; **

;; **
;;; *1.  What does a streak length of 1 mean, i.e. how many hits and misses are in a 
;;;     streak of 1? What about a streak length of 0?*
;; **

;; **
;;; Here we provide the custom function `calc-streak`, which may be 
;;; used to calculate the lengths of all shooting streaks and then look at the 
;;; distribution.
;; **

;; @@
(defn calc-streak [ds]
  (map (fn [[streak _]] (count streak))
       (take-while (fn [[streak shots]] (or ((complement empty?) streak) ((complement empty?) shots)))
                   (iterate #(split-with (partial = "H") (rest (second %))) 
                            (split-with (partial = "H") ds)))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.probability/calc-streak</span>","value":"#'openintro.probability/calc-streak"}
;; <=

;; @@
(calc-streak (i/sel kobe :cols :basket :rows (range 9)))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-unkown'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-unkown'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-unkown'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-unkown'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-unkown'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-unkown'>0</span>","value":"0"}],"value":"(1 0 2 0 0 0)"}
;; <=

;; @@
(def kobe-streak (i/dataset [:streak] (calc-streak (i/sel kobe :cols :basket))))

(def kobe-streak-chart
  (i/with-data
    (i/$order :streak :asc (i/$rollup :count :total :streak kobe-streak))
    (c/bar-chart :streak :total)))

(chart-view kobe-streak-chart)
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x1e095788 \"org.jfree.chart.JFreeChart@1e095788\"], :opts nil}"}
;; <=

;; **
;;; Note that instead of making a histogram, we chose to make a bar plot from a 
;;; table of the streak data. A bar plot is preferable here since our variable is 
;;; discrete -- counts -- instead of continuous.
;; **

;; **
;;; *2.  Describe the distribution of Kobe's streak lengths from the 2009 NBA finals. 
;;;     What was his typical streak length? How long was his longest streak of baskets?*
;; **

;; **
;;; ## Compared to What?
;;; 
;; **

;; **
;;; We've shown that Kobe had some long shooting streaks, but are they long enough 
;;; to support the belief that he had hot hands? What can we compare them to?
;;; 
;;; To answer these questions, let's return to the idea of *independence*. Two 
;;; processes are independent if the outcome of one process doesn't effect the outcome 
;;; of the second. If each shot that a player takes is an independent process, 
;;; having made or missed your first shot will not affect the probability that you
;;; will make or miss your second shot.
;;; 
;;; A shooter with a hot hand will have shots that are *not* independent of one 
;;; another. Specifically, if the shooter makes his first shot, the hot hand model 
;;; says he will have a *higher* probability of making his second shot.
;;; 
;;; Let's suppose for a moment that the hot hand model is valid for Kobe. During his
;;; career, the percentage of time Kobe makes a basket (i.e. his shooting 
;;; percentage) is about 45%, or in probability notation,
;;; 
;;; $$ P(\textrm{shot 1 = H}) = 0.45 $$
;;; 
;;; If he makes the first shot and has a hot hand (*not* independent shots), then 
;;; the probability that he makes his second shot would go up to, let's say, 60%,
;;; 
;;; $$ P(\textrm{shot 2 = H} \, | \, \textrm{shot 1 = H}) = 0.60 $$
;;; 
;;; As a result of these increased probabilites, you'd expect Kobe to have longer 
;;; streaks. Compare this to the skeptical perspective where Kobe does *not* have a
;;; hot hand, where each shot is independent of the next. If he hit his first shot,
;;; the probability that he makes the second is still 0.45.
;;; 
;;; $$ P(\textrm{shot 2 = H} \, | \, \textrm{shot 1 = H}) = 0.45 $$
;;; 
;;; In other words, making the first shot did nothing to effect the probability that
;;; he'd make his second shot. If Kobe's shots are independent, then he'd have the 
;;; same probability of hitting every shot regardless of his past shots: 45%.
;;; 
;;; Now that we've phrased the situation in terms of independent shots, let's return
;;; to the question: how do we tell if Kobe's shooting streaks are long enough to 
;;; indicate that he has hot hands? We can compare his streak lengths to someone
;;; without hot hands: an independent shooter. 
;; **

;; **
;;; ## Simulations in Incanter
;; **

;; **
;;; While we don't have any data from a shooter we know to have independent shots, 
;;; that sort of data is very easy to simulate in Incanter. In a simulation, you set the 
;;; ground rules of a random process and then the computer uses random numbers to 
;;; generate an outcome that adheres to those rules. As a simple example, you can
;;; simulate flipping a fair coin with the following.
;; **

;; @@
(def outcomes ["head", "tail"])

(s/sample outcomes :size 1 :replacement true)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;tail&quot;</span>","value":"\"tail\""}
;; <=

;; **
;;; The vector `outcomes` can be thought of as a hat with two slips of paper in it: 
;;; one slip says `heads` and the other says `tails`. The function `sample` draws 
;;; one slip from the hat and tells us if it was a head or a tail. 
;;; 
;;; Run the second command listed above several times. Just like when flipping a 
;;; coin, sometimes you'll get a heads, sometimes you'll get a tails, but in the 
;;; long run, you'd expect to get roughly equal numbers of each.
;;; 
;;; If you wanted to simulate flipping a fair coin 100 times, you could either run 
;;; the function 100 times or, more simply, adjust the `size` argument, which 
;;; governs how many samples to draw (the `:replacement true` (default is `true`) argument indicates we put
;;; the slip of paper back in the hat before drawing again). Save the resulting 
;;; vector of heads and tails in a new object called `sim-fair-coin`.
;; **

;; @@
(def sim-fair-coin
  (s/sample outcomes :size 100 :replacement true))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.probability/sim-fair-coin</span>","value":"#'openintro.probability/sim-fair-coin"}
;; <=

;; **
;;; Lets have a look at the results of this simulation.
;; **

;; @@
(println sim-fair-coin)

(print
  (i/$rollup :count :total :side
             (i/dataset [:side] sim-fair-coin)))
;; @@
;; ->
;;; (tail head tail tail tail tail tail head tail head tail head tail head tail head head tail head tail head head head tail tail head tail head tail head tail tail tail head head tail head head tail head head head tail tail head head tail head tail tail tail head tail head head head head tail tail head head head head head tail tail tail tail tail tail head tail head head tail tail tail head tail head head tail head head head head tail head tail head head tail head head tail head head tail tail head)
;;; 
;;; | :side | :total |
;;; |-------+--------|
;;; |  tail |     48 |
;;; |  head |     52 |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; Since there are only two elements in `outcomes`, the probability that we "flip" 
;;; a coin and it lands heads is 0.5. Say we're trying to simulate an unfair coin 
;;; that we know only lands heads 20% of the time. For that you can do the following trick
;; **

;; @@
(def sim-unfair-coin
  (map outcomes (s/sample-binomial 100 :prob 8/10 :size 1)))

(print
  (i/$rollup :count :total :side
             (i/dataset [:side] sim-unfair-coin)))
;; @@
;; ->
;;; 
;;; | :side | :total |
;;; |-------+--------|
;;; |  tail |     77 |
;;; |  head |     23 |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; Here we used the binomial distribution simulation with one experiment and probability of positive outcome of 0.8. Which gives us the probability of exactly one success in the experiment is equal to `0.8` and probability of zero positive outcomes is equal to `0.2`. The sequence of such experiments produces the sequence of `0` and `1` which we are using to draw `head` or `tail` from our outcome space by index. 
;; **

;; **
;;; *3.  In your simulation of flipping the unfair coin 100 times, how many flips 
;;;     came up heads?*
;; **

;; **
;;; In a sense, we've shrunken the size of the slip of paper that says "heads", 
;;; making it less likely to be drawn and we've increased the size of the slip of 
;;; paper saying "tails", making it more likely to be drawn. When we simulated the 
;;; fair coin, both slips of paper were the same size.
;; **

;; **
;;; ## Simulating the Independent Shooter
;; **

;; **
;;; Simulating a basketball player who has independent shots uses the same mechanism 
;;; that we use to simulate a coin flip. To simulate a single shot from an 
;;; independent shooter with a shooting percentage of 50% we type,
;; **

;; @@
(def outcomes ["H", "M"])

(def sim-basket
  (s/sample outcomes :size 1 :replacement true))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.probability/sim-basket</span>","value":"#'openintro.probability/sim-basket"}
;; <=

;; **
;;; To make a valid comparison between Kobe and our simulated independent shooter, 
;;; we need to align both their shooting percentage and the number of attempted shots.
;; **

;; @@
(def sim-basket
  (map outcomes (s/sample-binomial (i/nrow kobe) :prob 0.55 :size 1)))

(print
  (i/$rollup :count :total :basket
             (i/dataset [:basket] sim-basket)))
;; @@
;; ->
;;; 
;;; | :basket | :total |
;;; |---------+--------|
;;; |       M |     74 |
;;; |       H |     59 |
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; With the results of the simulation saved as `sim-basket`, we have the data 
;;; necessary to compare Kobe to our independent shooter. We can look at Kobe's data 
;; **

;; @@
(println (i/sel kobe :cols :basket))

(println sim-basket)
;; @@
;; ->
;;; (H M M H H M M M M H H H M H H M M H H H M M H M H H H M M M M M M H M H M M H H H H M H M M H M M H M M H M H H M M H M H H M H M M M H M M M M H M H M M H M M H H M M M M H H H M M H M M H M H H M H M M H M M M H M H H H M H H H M H M H M M M M M M H M H M M M M H)
;;; (M M M H H H M M M H H H M H M H H M H M M H H M M M H H M M M M H H M M H M H H H M H M M M H M H H M M M M M H M H M H H M M H H M H H H H M M H M M M H M M M M H H M M H M M M M M H H M M H M M M M H M H H M M H M H H H H H H M M M M M H H H H M M M M H H M M H M)
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; Both data sets represent the results of 133 shot attempts, each with the same 
;;; shooting percentage of 45%. We know that our simulated data is from a shooter 
;;; that has independent shots. That is, we know the simulated shooter does not have
;;; a hot hand.
;; **

;; **
;;; ### Comparing Kobe Bryant to the Independent Shooter
;; **

;; **
;;; Using `calc-streak`, let's compute the streak lengths of `sim-basket`.
;; **

;; @@
(def shooter-streak (i/dataset [:streak] (calc-streak sim-basket)))

(def shooter-streak-chart
  (i/with-data
    (i/$order :streak :asc (i/$rollup :count :total :streak shooter-streak))
    (c/bar-chart :streak :total)))

(chart-view shooter-streak-chart)
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x4cf36937 \"org.jfree.chart.JFreeChart@4cf36937\"], :opts nil}"}
;; <=

;; **
;;; * * *
;;; 
;;; -   Describe the distribution of streak lengths. What is the typical streak 
;;;     length for this simulated independent shooter with a 45% shooting percentage?
;;;     How long is the player's longest streak of baskets in 133 shots?
;;; 
;;; -   If you were to run the simulation of the independent shooter a second time, 
;;;     how would you expect its streak distribution to compare to the distribution 
;;;     from the question above? Exactly the same? Somewhat similar? Totally 
;;;     different? Explain your reasoning.
;;; 
;;; -   How does Kobe Bryant's distribution of streak lengths compare to the 
;;;     distribution of streak lengths for the simulated shooter? Using this 
;;;     comparison, do you have evidence that the hot hand model fits Kobe's 
;;;     shooting patterns? Explain.
;; **
