<b>User Story</b>
<ol>
    <li>Admin user opens (creates) an order book for a given instrument (string). In return gets a book with unique order book identifier.</li> 
    <li>Regular users start adding (creating) buy orders to the selected open order books. An order has a quantity (long), type (market or limit) and a price (number) if order is of limit type. In return, users get a unique order identifier.</li>
    <li>Admin user closes an order book, passing in the process total executed quantity (long) and execution price (number). Distribution algorithm kicks in. First validates orders (limit price must be equal or greater than execution price). Then distributes executed quantity proportionally among valid orders, but not above their ordered quantity.</li>
    <li>Regular users query their order status, price and effective executed quantity.</li>
</ol>


<ul>
    <li>Example (for a single order book) Orders:
        <p>id: 1, quantity: 50, type: limit, price: 17.0</p>
        <p>id: 2, quantity: 130, type: limit, price: 14.0</p>
        <p>id: 3, quantity: 150, type: market</p>
    </li>
    <li>Execution (order book closure):
        <p>quantity: 80, price: 15.0</p>
    </li>
    
   <li> Query result (orders):
        <p>id: 1, status: executed, quantity: 20, price: 15.0</p>
        <p>id: 2, status: invalid, quantity: 0, price: 15.0</p>
        <p>id: 3, status: executed, quantity: 60, price: 15.0</p>
    </li>
</ul>

<p><small><b>Authorization and authentication is not implemented thus all the endpoints are accessible by all users at the moment</b></small></p>
<p><small><b>The API's exposes the whole data model, it can be further be refactored and out put can be filtered </b></small></p>

<p> </p>
<b>Building and Running the application</b>
<ol>
    <li>  System Configurations 
            <ul> 
                <li>Java 1.8</li>
               <li> Maven </li>
            </ul>
    </li>
    <li> Navigate to the root folder of the project (i.e. where the pom.xml file is located) and execute
        <ul>
            <li> <code>mvn clean install</code> 
            <p>if this is successful there will be a folder named "target" </p> 
            <p> Navigate into the folder name "target", the file named "cs-booking-0.0.1-SNAPSHOT.jar" will exists</p>
            </li>
         </ul>
    </li>
    <li> To startup the server from the command line execute following command inside the target folder
        <ul>
            <li>
                <code>java -jar cs-booking-0.0.1-SNAPSHOT.jar</code>
                <p> When the server is started open a browser and navigate to <i>http://localhost:8080/swagger-ui.html</i></p>
                <p> All the REST  endpoints are accessible from swagger </p>
            </li>
        </ul>
    </li>
</ol>

<p>The solution demonstrates a RESTFull services using spring boot, hibernate, spring data repositories, JPA,  H2 database, and swagger documentation
 Test coverage could be  increased by adding  unit, integration and functional, 
 few test cases are added to demonstrate mocking, and integration testing</p>
 
 <p>
 EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS PROVIDE THE SOFTWARE "AS IS" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 
 <p>All trademarks acknowledged!</p>
 </p>
 
 