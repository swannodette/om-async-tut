(ns {{name}}.core
  (:require [ring.util.response :refer [file-response]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.edn :refer [wrap-edn-params]]
            [compojure.core :refer [defroutes GET PUT]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [datomic.api :as d]))

(def uri "datomic:free://localhost:4334/{{sanitized}}")
(def conn (d/connect uri))

(defn index []
  (file-response "public/html/index.html" {:root "resources"}))

(defn generate-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/edn"}
   :body (pr-str data)})

(defn update-class [id params]
  (let [db    (d/db conn)
        title (:class/title params)
        eid   (ffirst
                (d/q '[:find ?class
                       :in $ ?id
                       :where 
                       [?class :class/id ?id]]
                  db id))]
    (d/transact conn [[:db/add eid :class/title title]])
    (generate-response {:status :ok})))

(defn classes []
  (let [db (d/db conn)
        classes
        (vec (map #(d/touch (d/entity db (first %)))
               (d/q '[:find ?class
                      :where
                      [?class :class/id]]
                 db)))]
    (generate-response classes)))

(defroutes routes
  (GET "/" [] (index))
  (GET "/classes" [] (classes))
  (PUT "/class/:id/update"
    {params :params edn-params :edn-params}
    (update-class (:id params) edn-params))
  (route/files "/" {:root "resources/public"}))

(def handler 
  (-> routes
      wrap-edn-params))
