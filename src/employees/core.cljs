(ns employees.core
  (:require
   [reagent.core :as r]
   [cljs.test :refer-macros [is]])
  (:require-macros
   [devcards.core :as dc :refer [doc mkdn-pprint-code mkdn-pprint-source defcard defcard-rg defcard-doc deftest]]))

(enable-console-print!)

(defcard title
  "# Programação interativa com ClojureScript")

(defcard about
  "## Hugo Bessa

  ### Dev frontend @ Xerpa")

(defcard code-as-data
  (doc
   "# Código é dado"

   "Em um LISP o código é uma estrutura de dados"

   (mkdn-pprint-code '(+ 1 2 (/ 3 4)))
   (mkdn-pprint-code '(if (> a b)
                        (+ a b)
                        (+ b a)))))

(defcard calls
  (doc
   "# ClojureScript é um LISP"

   "## Uma chamada"
   (mkdn-pprint-code '(+ 1 2))))

(defcard prefix-notation
  (doc
   "## Notação prefixa, diferente de outras linguagens"
   (mkdn-pprint-code '(function arg1 arg2))
   (mkdn-pprint-code '(map even? '(1 2 3 4)))))

(defcard data-structures
  (doc
   "## ClojureScript é funcional"

   "### Estruturas de dados imutáveis"
   (mkdn-pprint-code ''(1 2 3))
   (mkdn-pprint-code '[1 2 3])
   (mkdn-pprint-code '#{1 2 3})
   (mkdn-pprint-code '{:a 1 :b 2 :c 3})))

(defcard core-functions
  (doc
   "### Sua API é bem completa"

   (mkdn-pprint-code '(map even? [1 2 3 4 5 6]))
   (mkdn-pprint-code '(filter zero? [1 2 3 4 5 6]))
   (mkdn-pprint-code '(every? int? [1 2 3 4 5 6]))
   (mkdn-pprint-code '(->> [0 1 2 3]
                           (remove zero?)
                           (map (partial * 3))
                           (map (juxt identity odd?))))))

(defn search-input
  [props text]
  [:input (merge {:class "search-input"
                  :default-value text})])

(defcard react
  (doc
   "## Sua integração com React é muito boa"

   "Para nossos exemplos vamos utilizar um wrapper chamado *reagent*"

   "Ele deixa a gente criar componentes que são apenas funções"

   (mkdn-pprint-source search-input)))

#_(defcard react-no-jsx
  (doc
   "Não precisamos de JSX."

   "Apenas utilizamos um vetor:"

   (mkdn-pprint-code '[:input props])
   (mkdn-pprint-code '[:div props children])))

(defcard enough
  (doc
   "Esta palestra não é sobre ClojureScript"

   "## É sobre Programação Interativa"))

(defcard summary
  (doc
   "
- Utilizo para construir a Xerpa
- Melhorou muito meu workflow
- Promove feedbacks rápidos
"

   ;; ATIVAR
   (mkdn-pprint-code (map identity ["you" "are" "awesome"]))))

(defcard tech
  (doc
   ;; ADICIONAR LINKS
   "Usarei:

- [devcards]() para construir essa interface interativa
- [figwheel]() para recarregar o código no browser"))

(def employee-data
  {:name "Paulo Ahagon"
   :position "Chefe"})

(defn employee [props]
  [:div {:class "employee"}
   [:span {:class "employee-name"}  (get props :name)]
   [:span {:class "employee-position"} (get props :position)]])

(defcard start
  (doc
   "## Vamos contruir uma lista de funcionários"

   "Estrutura de dados (funcionário)"

   (mkdn-pprint-source employee-data)))

(defcard employee-comp
  (doc
   "Componente do funcionário"

   (mkdn-pprint-source employee)))

(defcard-rg employee-display
  [employee employee-data]
  employee-data
  {:inspect-data true})

(def employee-data-list
  [{:name "Paulo Ahagon" :position "Chefe"}
   {:name "Nick"         :position "Gringo"}
   {:name "Milhouse"     :position "Engenheiro de Software"}])

(defcard employee-list-data
  (doc
   "Estrutura para lista de funcionários"

   (mkdn-pprint-source employee-data-list)))

(defn employee-list [props]
  [:ul {:class "employee-list"}
   (for [e (:employees props)]
     [:li [employee e]])])

(defcard employee-list-comp
  "Componente de listagem de funcionários"

  (mkdn-pprint-source employee-list))

(defcard-rg employee-list-display
  [employee-list {:employees employee-data-list}]
  {:employees employee-data-list}
  {:inspect-data true})

(defcard search-data
  "Para filtrar, usaremos uma string de texto"
  (mkdn-pprint-code '{:query "Paulo"}))

(defonce state (r/atom {:query ""
                        :employees [{:name "Daniel Filho" :position "Desenvolvedor front-end"}
                                    {:name "Diego Souza"  :position "Engenheiro de Software"}
                                    {:name "Milhouse"     :position "Engenheiro de Software"}
                                    {:name "Nick"         :position "Gringo"}
                                    {:name "Paulo Ahagon" :position "Chefe"}]}))

(defn change-query [query]
  (swap! state assoc :query query))

(defn on-change [e]
  (change-query (-> e .-target .-value)))

(defn search [props]
  [:input {:class "search"
           :value (:query props)
           :on-change (:on-change props)
           :placeholder "Filtrar..."}])

(defcard search-comp
  "Nosso componente de campo de busca"
  (mkdn-pprint-source search))

(defcard-rg search-display
  [search {:query "Paulo"}]
  {:query "Paulo"}
  {:inspect-data true})

(defcard compose-state
  "Agora juntamos os dados para formar o estado da nossa aplicação"

  (mkdn-pprint-code '{:query ""
                      :employees [{:name "Daniel Filho" :position "Desenvolvedor front-end"}
                                  {:name "Diego Souza"  :position "Engenheiro de Software"}
                                  {:name "Milhouse"     :position "Engenheiro de Software"}
                                  {:name "Nick"         :position "Gringo"}
                                  {:name "Paulo Ahagon" :position "Chefe"}]}))

(defcard about-state
  (doc
   "Para o [reagent](), nosso estado é um `atom`."

   "Uma referência para os diversos valores que ele assumirá durante a execução da aplicação."

   (mkdn-pprint-source state)))

(defcard about-state-change
  (doc
   "Podemos mudar nosso estado usando a função `swap!` em conjunto com a `assoc`."

   (mkdn-pprint-source change-query)))

(defn query-filter [query employees]
  (let [re-query (re-pattern (str "^" query))]
    (filter (fn [emp]
              (re-find re-query (:name emp)))
     employees)))

(defcard filter-impl
  (doc
   "Ao digitar uma busca, precisamos filtrar os funcionários"

   "A função que faz isso é a `query-filter`:"
   (mkdn-pprint-source query-filter)

   "Podemos incluir também alguns tests, para termos certeza de que tudo está funcionando"))

(def test-employees
  [{:name "Paulo"} {:name "Nick"} {:name "Milhouse"}])

(defcard test-data
  (mkdn-pprint-source test-employees))

(deftest filter-tests
  (is (= (query-filter "P" test-employees)
         [{:name "Paulo"}]))

  (is (= (query-filter "Paulo" test-employees)
         [{:name "Paulo"}]))

  (is (= (query-filter "Ni" test-employees)
         [{:name "Nick"}]))

  ;;; REMOVER O "i" por ""
  (is (= (query-filter "" test-employees)
         test-employees)))

(defn app []
  [:div {:class "app"}
   [search {:query (:query @state)
            :on-change on-change}]
   [employee-list {:employees (query-filter (:query @state)
                                            (:employees @state))}]])

(defcard compose-app
  "Juntando os componentes que criamos, temos o nosso app"
  (mkdn-pprint-source app))

(defcard-rg app-display
  [app]
  state
  {:inspect-data true
   :history true})

(defcard css
  "No devcards, o css também é carregado automaticamente")

(defcard at-xerpa
  "Na Xerpa, usamos o devcards para documentar todos os componentes de interface")

(defcard work-at-xerpa
  "### Estamos procurando devs frontend :)"

  "hbessa@xerpa.com.br")
