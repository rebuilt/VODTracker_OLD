package org.homelinux.client;

/**
 *A container for storing the exclusion/inclusion criteria of a sql statement.  Specifically it contains the predicate of a where clause.
 * <code> http://en.wikipedia.org/wiki/File:SQL_ANATOMY_wiki.svg </code>
 * <br/>For example: <br/>  author like 'diggity%'.  <br/> When
 * constructing multiple FilterElements, each instance of a FilterElement is passed to the constructor of each subsequent FilterElement to
 * initialize the pointer.  <br/> For example:  <p>  <code>         FilterElement filt =
new FilterElement("DiggitySC", "Diggity", FilterElement.AUTHOR, FilterElement.Operator.LIKE); <br/>

filt =
new FilterElement("Klazartsc", "klazart", FilterElement.AUTHOR, FilterElement.Operator.LIKE, filt);</p> </code>
 * <br/>
 * or by: <br/>   <code> FilterElement stringTest = new FilterElement("0_0_diggity_0_0"); </code> <br/>
 * The format is  type_operator_sqlFilter_combiner
 * @author nelson
 */
public class FilterElement extends Model implements Filter {

    private String sqlFilterElement,  sqlSearchString;
    public static final int AUTHOR = 0;
    public static final int DURATION = 1;
    public static final int TITLE = 2;
    public static final int RATING = 3;
    public static final int PUBLISHED = 4;
    public static final int DESCRIPTION = 5;
    private int type;
    private Filter filter = null;
    private Operator op;
    private Combiner co;
    private boolean openParens = false;
    private boolean closeParens = false;

    /**
     * Defines a method of comparison for a particular where clause.  For example:
     * <br/>
     * author like 'diggity%'
     * <br/>
     * LIKE, NOT, EQUALS, BETWEEN, GREATERTHAN, LESSTHAN
     * <br/>
     *
     */
    public enum Operator {

        LIKE, NOT, EQUALS, BETWEEN, GREATERTHAN, LESSTHAN
    }

    /**
     * Defines the text to add at the end of a where clause.  For example:
     * <br/>
     * AND, OR
     */
    public enum Combiner {

        AND, OR
    }

    public FilterElement()
    {

        type = 0;

        sqlFilterElement =
                "%";
        sqlSearchString =
                "";
        op =
                Operator.LIKE;

    }

    public FilterElement(String query)
    {
        createElement(query);
    }

    public FilterElement(String query, FilterElement filt)
    {
        this(query);
        filter = filt;
    }

    public FilterElement(String SQLText, int filterType)
    {

        sqlFilterElement = SQLText;
        type = filterType;
        op = Operator.LIKE;

    }

    public FilterElement(String SQLText, int filterType, FilterElement filt)
    {
        this(SQLText, filterType);
        filter =
                filt;
    }

    public FilterElement(String dispName, String SQLText, int filterType)
    {
        this(SQLText, filterType);



    }

    public FilterElement(String SQLText, int filterType, Operator operator)
    {
        this(SQLText, SQLText, filterType);

        op = operator;

    }

    public FilterElement(String SQLText, int filterType, Operator operator, Filter filt)
    {
        this(SQLText, SQLText, filterType, operator);
        filter =
                filt;


    }

    public FilterElement(String text, int type, Operator op, Combiner comb)
    {
        this(text, type, op);
        this.co = comb;
    }

    public FilterElement(String dispName, String SQLText, int filterType, Operator operator)
    {
        this(dispName, SQLText, filterType);

        op =
                operator;

    }

    public FilterElement(String dispName, String SQLText, int filterType, Operator operator, Filter filt)
    {
        this(dispName, SQLText, filterType, operator);
        filter =
                filt;
    }

    /**
     * Create a filter element from a short string representation in the format type_operator_sqlFilter_combiner
     * @param query a short string representation of a FilterElement
     * @see getDigest()
     */
    private void createElement(String query)
    {
        String[] elements = new String[5];

        elements = query.split("_");

        setType(Integer.parseInt(elements[0]));
        setOperator(Integer.parseInt(elements[1]));
        setSqlFilterElement(elements[2]);
        setCombiner(Integer.parseInt(elements[3]));
    }

    /** Insert an open parenthesis before this element
     *
     */
    public void openParens()
    {
        openParens = true;
    }

    /**
     * Insert an close parenthesis after this element
     */
    public void closeParens()
    {
        closeParens = true;
    }

    /**
     * Recursivley cycle through all the chained FilterElements, aggregate each filter detail and return a string representation.
     * A string representation can be created by calling getDigest().
     * @return a string representation of all connected FilterElements.
     */
    public String toString()
    {
        sqlSearchString = "";
        if (openParens) {
            sqlSearchString = " ( ";
        }

        switch (type) {
            case 0:
                sqlSearchString += " author ";
                break;

            case 1:
                sqlSearchString += " duration ";
                break;

            case 2:
                sqlSearchString += " title ";
                break;

            case 3:
                sqlSearchString += " gosuRating ";
                break;

            case 4:
                sqlSearchString += " published ";
                break;

            case 5:
                sqlSearchString += " description ";
                break;

        }


        switch (op) {
            case LIKE:
                sqlSearchString += " like '%" + sqlFilterElement + "%' ";
                break;

            case NOT:
                sqlSearchString += " not like '%" + sqlFilterElement + "%' ";
                break;

            case EQUALS:
                sqlSearchString += " = '" + sqlFilterElement + "' ";
                break;

            case BETWEEN:
                sqlSearchString += " between " + sqlFilterElement + " ";
                break;

            case GREATERTHAN:
                sqlSearchString += ">" + sqlFilterElement + " ";
                break;

            case LESSTHAN:
                sqlSearchString += "<" + sqlFilterElement + " ";
                break;

        }


        String operate = " or ";
        if (co == null) {

            if (op == Operator.NOT || type == this.PUBLISHED) {
                operate = " and ";
                sqlSearchString =
                        sqlSearchString.replaceFirst("%", "");
            }

        } else {
            switch (co) {
                case AND:
                    operate = " and ";
                    break;

                case OR:
                    operate = " or ";
                    break;

            }


        }
        if (filter != null) {
            sqlSearchString = filter.toString() + operate + sqlSearchString;
        }
        if (closeParens) {
            sqlSearchString += " ) ";
        }

        return sqlSearchString;
    }

    public void add(FilterElement filt)
    {
        if (filter != null) {
            ((FilterElement) filter).add(filt);

        } else {
            filter = filt;
        }

    }

    /**
     * Creates a short String representation of this FilterElement in the format   type_operator_sqlFilter_combiner
     * @return A short String representation of this FilterElement
     */
    public String getDigest()
    {
        // type / operator / sqlFilter  / combiner

        String output = "";

        if (filter != null) {
            output +=
                    ((FilterElement) filter).getDigest() + "~" + getType() + "_" + op.ordinal() + "_" + this.sqlFilterElement + "_" + co.ordinal();
        } else if (filter == null) {
            output +=
                    getType() + "_" + op.ordinal() + "_" + this.sqlFilterElement + "_" + co.ordinal();
        }
        return output;
    }

    /**
     * Set the type of object.  Options include <code> FilterElement.AUTHOR , FilterElement.DURATION , FilterElement.TITLE , FilterElement.RATING , FilterElement.PUBLISHED , FilterElement.DESCRIPTION</code>
     * @param the attribute by which this element will filter
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     *
     * @return The type of data by which this element filters
     */
    public int getType()
    {
        return type;
    }

    public String getSqlFilterText()
    {
        String output = "";
        if (filter != null) {
            output =
                    ((FilterElement) filter).getSqlFilterText() + "~" + sqlFilterElement;
        } else if (filter == null) {
            output += sqlFilterElement;
        }

        return output;
    }

    public void setSqlFilterText(String sqlFilterText)
    {
        this.sqlFilterElement = sqlFilterText;
    }

    /**
     * Determines whether this element ends with or / and <code> Combiner.AND , Combiner.OR </code>
     * @param combiner
     */
    private void setCombiner(int combiner)
    {
        co = Combiner.values()[combiner];
    }

    /**
     * The method of comparison <code> Operator.BETWEEN , Operator.EQUALS, Operator.GREATERTHAN </code>
     * @param oper the method of comparison
     */
    private void setOperator(int oper)
    {
        op = Operator.values()[oper];
    }

    /** The text element to search for.  For example:  'diggity'
     *
     * @param sqlFilterElement The text element to search for
     */
    public void setSqlFilterElement(String sqlFilterElement)
    {
        this.sqlFilterElement = sqlFilterElement;
    }

    /**
     * The entire string to be used in the sql statement.  For Example : author like 'diggity'
     * @param sqlSearchString The entire string to be used in the sql statement.
     */
    public void setSqlSearchString(String sqlSearchString)
    {
        this.sqlSearchString = sqlSearchString;
    }

    public String getSqlFilterElement()
    {
        return sqlFilterElement;
    }

    public String getSqlSearchString()
    {
        return sqlSearchString;
    }
}
