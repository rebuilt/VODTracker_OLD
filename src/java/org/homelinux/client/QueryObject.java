
package org.homelinux.client;

import java.io.Serializable;

/**
 * Provides a container for constructing an sql query.
 * @author nelson
 */
public class QueryObject extends Model implements Filter, Serializable {

    private int startIndex;
    private int numOfResults;//this is the number of results we want to request
    private Ordering od = Ordering.PUBLISHED_DESC;

    public Ordering getOd()
    {
        return od;
    }
    /**
     * Set the ordering for the results returned.
     *
     * @param od
     * @see Ordering
     */
    public void setOrdering(Ordering od)
    {
        this.od = od;
    }


    private FilterElement filter;
    public static int RATINGS = 0;
    public static int MAJORBATTLES = 1;
    public static int PIMPESTPLAYS = 2;
    private int table = 0;

    public QueryObject()
    {
        initialize();
    }

    public QueryObject(Filter query)
    {
        initialize();
        this.filter = (FilterElement) query;
    }

    public QueryObject(Filter query, int start, int total)
    {
        this(query);
        startIndex = start;
        numOfResults = total;
    }
/**
 * Sets the sql Filter which holds the information for the specific text by which to search the mysql database.  For example: <code> author like 'diggity%'</code>.
 * @param query
 * @see FilterElement
 */
    public void setFilter(Filter query)
    {
        this.filter = (FilterElement) query;
    }


/**
 * Returns the Filter that holds the information for the specific text by which to search the mysql database.  For example: <code> author like 'diggity%'</code>.
 * @return
 */
    public FilterElement getFilter()
    {
        return filter;
    }

    /**
     * Compares two QueryObjects and returns true if the start index,
     * number of results, and the query string are all the same
     * @param obj A QueryOject to be compared
     * @return returns true if the QueryObjects are equal, false if they are not
     */
    public boolean equals(Object obj)
    {
        if(obj instanceof QueryObject ){
            return this.getIdentity().equals(((QueryObject) obj).getIdentity());
        }
        return false;
    }

    /**
     * adds all the attributes of a QueryObject class 
     * @return the string generated from adding all QueryObject attributes
     */
    public String getIdentity()
    {
        return startIndex + numOfResults + filter.toString();
    }

    public int hashCode()
    {     
        return 31 * 7 + filter.hashCode() + startIndex + numOfResults;
    }

// {"VIDEOID", "PUBLISHED", "TITLE", "AUTHOR", "URI", "DESCRIPTION", "PLAYERURL", "THUMBNAILURL", "DURATION", "GOSURATING"};
    /**
     * Returns a string representation of the entire sql search string.  This includes the Select, Where, and Order By clauses
     * @return sql statement
     */
    public String toString()
    {
        String out = "";
        switch (table) {
            case 0:
                out +=
                        "Select VIDEOID, PUBLISHED, TITLE, AUTHOR, URI, DESCRIPTION, PLAYERURL, THUMBNAILURL, DURATION, GOSURATING from videos where ";
                break;
            case 1:
                out += "Select * from majorbattles where ";
                break;
            case 2:
                out += "Select * from pimpestplays where ";
                break;
        }

        return (out + filter.toString() + ordering());
    }

    private void initialize()
    {
        table = 0;
        startIndex = 0;
        numOfResults = 12;
        filter = new FilterElement();
    }
/**
 * resets all the values of this QueryObject <code> table = 0;<br/>
        startIndex = 0;<br/>
        numOfResults = 12;<br/>
        filter = new FilterElement();</code>
 */
    public void reset()
    {
        initialize();
    }

    public String getQuery()
    {
        return filter.toString();
    }

    /**
     * After the sql query is run, the startIndex determines the first element in the results to return.
     * The startIndex is used to page through results.
     * @return the index at which to begin returning results
     */
    public int getStartIndex()
    {
        return startIndex;
    }

    /**
     * Get the number of videos the sql query will return
     * @return number of search results
     *
     */
    public int getNumofResults()
    {
        return this.numOfResults;
    }
    /**
     * Limit the  number of search results to return
     *
     */
    public void setNumOfResults(int numOfResults)
    {
        this.numOfResults = numOfResults;
    }

      /**
     * After the sql query is run, the startIndex determines the first element in the results to return.
     * The startIndex is used to page through results.
     * @return
     */
    public void setStartIndex(int startIndex)
    {
        this.startIndex = startIndex;
    }

   /**
    * Returns a text representation of an <code> Ordering </code> element
    * @return text representation of an <code> Ordering </code> element
    * @see Ordering
    */
    private String ordering()
    {
        String output = "";

        switch (od) {
            case NONE:
                //do nothing
                break;
            case AUTHOR_ASC:
                output += " order by author ";
                break;

            case AUTHOR_DESC:
                output += " order by author desc ";
                break;

            case PUBLISHED_ASC:
                output += " order by published ";
                break;

            case PUBLISHED_DESC:
                output += " order by published desc ";
                break;

            case TITLE_ASC:
                output += " order by title ";
                break;

            case TITLE_DESC:
                output += " order by title desc ";
                break;

            case GOSURATING_ASC:
                output += " order by gosurating ";
                break;

            case GOSURATING_DESC:
                output += " order by gosurating desc";
                break;

            case DURATION_ASC:
                output += " order by duration ";
                break;

            case DURATION_DESC:
                output += " order by duration desc ";
                break;

        }

        return output;
    }
}
