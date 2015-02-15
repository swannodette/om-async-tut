(ns {{name}}.core
  (:require [cljs.reader :as reader]
            [figwheel.client :as fw]
            [goog.events :as events]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true])
  (:import [goog.net XhrIo]
           goog.net.EventType
           [goog.events EventType]))

(enable-console-print!)

(println "Hello world!")

(fw/start {})
