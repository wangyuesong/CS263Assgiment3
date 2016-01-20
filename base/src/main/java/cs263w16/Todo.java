
package cs263w16;

import javax.xml.bind.annotation.XmlRootElement;

/**  
 * @Project: base
 * @Title: Todo.java
 * @Package cs263w16
 * @Description: TODO
 * @author YuesongWang
 * @date Jan 19, 2016 11:04:40 AM
 * @version V1.0  
 */
@XmlRootElement
public class Todo {
    private String id;
    private String summary;
    private String description;
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Todo() {
        super();
    }
    public Todo(String summary, String description) {
        super();
        this.summary = summary;
        this.description = description;
    }
    public String getSummary() {
      return summary;
    }
    public void setSummary(String summary) {
      this.summary = summary;
    }
    public String getDescription() {
      return description;
    }
    public void setDescription(String description) {
      this.description = description;
    }

}
