package it.unibo.conversational.datatypes;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import it.unibo.conversational.database.Cube;
import org.jgrapht.Graph;
import org.jgrapht.alg.lca.NaiveLCAFinder;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.Set;
import java.util.stream.Collectors;

public class DependencyGraph {

    private static Graph<String, DefaultEdge> getWateringDependencies() {
        final DefaultDirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);
        g.addVertex("type");
        g.addVertex("measurement_type");
        g.addEdge("type", "measurement_type");

        // TIME
        g.addVertex("timestamp");
        g.addVertex("hour");
        g.addEdge("timestamp", "hour");
        g.addVertex("day");
        g.addEdge("hour", "day");
        g.addVertex("month");
        g.addEdge("day", "month");
        g.addVertex("week");
        g.addEdge("day", "week");
        g.addVertex("year");
        g.addEdge("month", "year");
        g.addVertex("all_date");
        g.addEdge("year", "all_date");

        // AGENT
        g.addVertex("agent");
        g.addVertex("agent_type");
        g.addEdge("agent", "agent_type");
        g.addVertex("all_agent");
        g.addEdge("agent_type", "all_agent");

        // FIELD
        g.addVertex("field");
        g.addVertex("all_field");
        g.addEdge("field", "all_field");

        return g;
    }

    private static Graph<String, DefaultEdge> getCimiceDependencies() {
        final DefaultDirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);
        // TIME
        g.addVertex("week");
        g.addVertex("month");
        g.addEdge("week", "month");
        g.addVertex("year");
        g.addEdge("month", "year");
        g.addVertex("all_date");
        g.addEdge("year", "all_date");

        // SPACE
        g.addVertex("gid");
        g.addVertex("province");
        g.addEdge("gid", "province");
        g.addVertex("region");
        g.addEdge("province", "region");
        g.addVertex("all_region");
        g.addEdge("region", "all_region");

        // CROP
        g.addVertex("crop_id");
        g.addVertex("crop_type");
        g.addEdge("crop_id", "crop_type");
        g.addVertex("all_crop");
        g.addEdge("crop_type", "all_crop");
        return g;
    }

    private static Graph<String, DefaultEdge> getCovidMartDependencies() {
        final DefaultDirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);
        // DATE
        g.addVertex("daterep");
        g.addVertex("month");
        g.addEdge("daterep", "month");
        g.addVertex("year");
        g.addEdge("month", "year");
        g.addVertex("all_date");
        g.addEdge("year", "all_date");

        // COUNTRY
        g.addVertex("geoid");
        g.addVertex("countriesandterritories");
        g.addEdge("geoid", "countriesandterritories");
        g.addVertex("continentexp");
        g.addEdge("countriesandterritories", "continentexp");
        g.addVertex("all_country");
        g.addEdge("continentexp", "all_country");
        return g;
    }

    private static Graph<String, DefaultEdge> getCovidWeeklyMartDependencies() {
        final DefaultDirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);
        // DATE
        g.addVertex("week");
        g.addVertex("month");
        g.addEdge("week", "month");
        g.addVertex("year");
        g.addEdge("month", "year");
        g.addVertex("allweek");
        g.addEdge("year", "allweek");

        // COUNTRY
        g.addVertex("country");
        g.addVertex("continent");
        g.addEdge("country", "continent");
        g.addVertex("population");
        g.addEdge("country", "population");
        g.addVertex("allcountry");
        g.addEdge("continent", "allcountry");
        return g;
    }
    
    public static Graph<String, DefaultEdge> getDependencies(final Cube cube) {
        switch (cube.getFactTable()) {
            case "ft_measurement":
                return getWateringDependencies();
            case "cimice_ft_captures":
                return getCimiceDependencies();
            case "covidfact":
                return getCovidMartDependencies();
            case "ft":
                return getCovidWeeklyMartDependencies();
            case "frencheletricityft":
            case "frencheletricityft_ext":
                return getFrenchElectDependencies();
            case "ft_sales":
            case "ft_purchase":
            case "ft_salpurch":
            case "sales_fact_1997":
                return getFoodMartDependencies();
            case "lineorder2": // ssb cube
            case "lineorder5": // ssb cube
            case "lineorder10": // ssb cube
            case "lineorder15": // ssb cube
            case "lineorder100": // ssb cube
                return getSSBDependencies();
        }
        throw new IllegalArgumentException(DependencyGraph.class + ": unknown schema " + cube.getFactTable());
    }

    private static Graph<String, DefaultEdge> getFrenchElectDependencies() {
        final DefaultDirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);
        g.addVertex("annee");
        g.addVertex("allannee");
        g.addEdge("annee", "allannee");

        g.addVertex("secteurnaf2");
        g.addVertex("grandsecteur");
        g.addEdge("secteurnaf2", "grandsecteur");
        g.addVertex("allsecteur");
        g.addEdge("grandsecteur", "allsecteur");

        g.addVertex("categorieconsommation");
        g.addVertex("allcategorie");
        g.addEdge("categorieconsommation", "allcategorie");

        g.addVertex("iris");
        g.addVertex("population");
        g.addEdge("iris", "population");
        g.addVertex("typeiris");
        g.addVertex("population");
        g.addEdge("iris", "typeiris");
        g.addEdge("iris", "population");
        g.addVertex("commune");
        g.addEdge("iris", "commune");
        g.addVertex("epci");
        g.addVertex("popcomm");
        g.addEdge("commune", "epci");
        g.addEdge("commune", "popcomm");
        g.addVertex("typeepci");
        g.addVertex("popepci");
        g.addEdge("epci", "popepci");
        g.addEdge("epci", "typeepci");
        g.addVertex("departement");
        g.addEdge("epci", "departement");
        g.addVertex("region");
        g.addEdge("departement", "region");
        g.addVertex("popdept");
        g.addEdge("departement", "popdept");
        g.addVertex("alliris");
        g.addEdge("region", "alliris");
        g.addVertex("popregion");
        g.addEdge("region", "popregion");
        g.addEdge("typeepci", "alliris");
        g.addEdge("typeiris", "alliris");
        return g;
    }

    private static Graph<String, DefaultEdge> getSSBDependencies() {
        final DefaultDirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);
        // PRODUCT
        g.addVertex("partkey");
        g.addVertex("product");
        g.addEdge("partkey", "product");
        g.addVertex("brand");
        g.addEdge("product", "brand");
        g.addVertex("category");
        g.addEdge("brand", "category");
        g.addVertex("allproduct");
        g.addEdge("category", "allproduct");
        // CUSTOMER
        g.addVertex("custkey");
        g.addVertex("customer");
        g.addEdge("custkey", "customer");
        g.addVertex("nation");
        g.addEdge("customer", "nation");
        g.addVertex("population");
        g.addVertex("region");
        g.addEdge("nation", "population");
        g.addEdge("nation", "region");
        g.addVertex("allcustomer");
        g.addEdge("region", "allcustomer");
        // SUPPLIER
        g.addVertex("suppkey");
        g.addVertex("supplier");
        g.addEdge("suppkey", "supplier");
        g.addVertex("s_nation");
        g.addEdge("supplier", "s_nation");
        g.addVertex("s_region");
        g.addEdge("s_nation", "s_region");
        g.addVertex("allsupplier");
        g.addEdge("s_region", "allsupplier");
        // DATE
        g.addVertex("datekey");
        g.addVertex("date");
        g.addEdge("datekey", "date");
        g.addVertex("month");
        g.addEdge("date", "month");
        g.addVertex("year");
        g.addEdge("month", "year");
        g.addVertex("alldate");
        g.addEdge("year", "alldate");
        return g;
    }

    private static Graph<String, DefaultEdge> getFoodMartDependencies() {
        final DefaultDirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);
        // PRODUCT
        g.addVertex("product_id");
        g.addVertex("brand_name");
        g.addEdge("product_id", "brand_name");
        g.addVertex("product_name");
        g.addEdge("product_id", "product_name");
        g.addVertex("product_subcategory");
        g.addEdge("product_id", "product_subcategory");
        g.addVertex("product_category");
        g.addEdge("product_subcategory", "product_category");
        // g.addVertex("product_department");
        // g.addEdge("product_subcategory", "product_department");
        g.addVertex("product_family");
        g.addEdge("product_subcategory", "product_family");
        g.addVertex("allproducts");
        g.addEdge("product_category", "allproducts");
        // g.addEdge("product_department", "allproducts");
        g.addEdge("product_family", "allproducts");
        // STORE
        g.addVertex("store_id");
        g.addVertex("store_name");
        g.addEdge("store_id", "store_name");
        g.addVertex("florist");
        g.addEdge("store_id", "florist");
        g.addVertex("coffee_bar");
        g.addEdge("store_id", "coffee_bar");
        g.addVertex("salad_bar");
        g.addEdge("store_id", "salad_bar");
        g.addVertex("video_store");
        g.addEdge("store_id", "video_store");
        g.addVertex("store_type");
        g.addEdge("store_id", "store_type");
        g.addVertex("store_city");
        g.addEdge("store_id", "store_city");
        g.addVertex("store_state");
        g.addEdge("store_city", "store_state");
        g.addVertex("store_country");
        g.addEdge("store_state", "store_country");
        g.addVertex("allstores");
        g.addEdge("store_country", "allstores");
        g.addEdge("store_name", "allstores");
        g.addEdge("store_type", "allstores");
        g.addEdge("florist", "allstores");
        g.addEdge("coffee_bar", "allstores");
        g.addEdge("salad_bar", "allstores");
        g.addEdge("video_store", "allstores");
        // CUSTOMER
        g.addVertex("customer_id");
        g.addVertex("yearly_income");
        g.addEdge("customer_id", "yearly_income");
        g.addVertex("fullname");
        g.addEdge("customer_id", "fullname");
        g.addVertex("member_card");
        g.addEdge("customer_id", "member_card");
        g.addVertex("gender");
        g.addEdge("customer_id", "gender");
        g.addVertex("occupation");
        g.addEdge("customer_id", "occupation");
        g.addVertex("marital_status");
        g.addEdge("customer_id", "marital_status");
        g.addVertex("city");
        g.addEdge("customer_id", "city");
        g.addVertex("state_province");
        g.addEdge("city", "state_province");
        g.addVertex("country");
        g.addEdge("state_province", "country");
        g.addVertex("population");
        g.addEdge("country", "population");
        g.addVertex("allcustomers");
        g.addEdge("yearly_income", "allcustomers");
        g.addEdge("fullname", "allcustomers");
        g.addEdge("member_card", "allcustomers");
        g.addEdge("gender", "allcustomers");
        g.addEdge("occupation", "allcustomers");
        g.addEdge("marital_status", "allcustomers");
        g.addEdge("country", "allcustomers");
        // DATE
        g.addVertex("time_id");
        g.addVertex("the_date");
        g.addEdge("time_id", "the_date");
        g.addVertex("the_month");
        g.addEdge("the_date", "the_month");
        g.addVertex("quarter");
        g.addEdge("the_date", "quarter");
        g.addVertex("the_year");
        g.addEdge("the_month", "the_year");
        g.addEdge("quarter", "the_year");
        g.addVertex("alldates");
        g.addEdge("the_year", "alldates");
        // PROMOTION
        g.addVertex("promotion_id");
        return g;
    }

    public static Optional<String> lca(final Cube cube, final String s1, final String s2) {
        final NaiveLCAFinder<String, DefaultEdge> lca = new NaiveLCAFinder<String, DefaultEdge>(getDependencies(cube));
        return Optional.fromNullable(lca.getLCA(s1.toLowerCase(), s2.toLowerCase()));
    }

    private static String edgeToString(final DefaultEdge e) {
        return e.toString().replace("(", "").replace(")", "").replace(" ", "").split(":")[1];
    }

    private static Set<DefaultEdge> filterDescriptiveEdges(final Set<DefaultEdge> e) {
        return e.stream().filter(i -> {
            final String edge = edgeToString(i);
            return !(edge.contains("pop") || edge.startsWith("all"));
        }).collect(Collectors.toSet());
    }

    public static Optional<String> getParent(final Cube cube, final String s1) {
        return Optional.fromJavaUtil(filterDescriptiveEdges(getDependencies(cube).outgoingEdgesOf(s1)).stream().map(DependencyGraph::edgeToString).sorted().findFirst());
    }

    public static Set<String> getParents(final Cube cube, final String s1, final boolean earlystop) {
        final Set<String> acc = Sets.newLinkedHashSet();
        final Set<DefaultEdge> edges = Sets.newHashSet(getDependencies(cube).outgoingEdgesOf(s1));
        while (!edges.isEmpty()) {
            final DefaultEdge first = edges.stream().findFirst().get();
            edges.remove(first);
            final String source = edgeToString(first);
            if (!(source.startsWith("pop"))) {
                acc.add(source);
            }
            if (!earlystop) {
                edges.addAll(getDependencies(cube).outgoingEdgesOf(source));
            }
        }
        return acc;
    }

    /**
     * Get the properties (i.e., descriptive attributes) linked to the given attribute.
     * @param cube cube
     * @param s1 attribute
     * @return properties
     */
    public static Set<String> getProperties(final Cube cube, final String s1) {
        final Set<String> acc = Sets.newLinkedHashSet();
        final Set<DefaultEdge> edges = Sets.newHashSet(getDependencies(cube).outgoingEdgesOf(s1));
        while (!edges.isEmpty()) {
            final DefaultEdge first = edges.stream().findFirst().get();
            edges.remove(first);
            final String source = edgeToString(first);
            if (source.startsWith("pop")) {
                acc.add(source);
            }
        }
        return acc;
    }
}
