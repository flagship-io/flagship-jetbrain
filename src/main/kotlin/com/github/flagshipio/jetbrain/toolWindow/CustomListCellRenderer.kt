import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBPanel
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.Component
import javax.swing.DefaultListModel
import javax.swing.DefaultListCellRenderer
import javax.swing.JButton
import javax.swing.JList
import javax.swing.JPanel

data class ListItem(val name: String, val action: () -> Unit)

class CustomListCellRenderer : DefaultListCellRenderer() {
    override fun getListCellRendererComponent(
        list: JList<*>?,
        value: Any?,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        if (value is ListItem) {
            val panel = JPanel(BorderLayout())
            val nameLabel = super.getListCellRendererComponent(list, value.name, index, isSelected, cellHasFocus)
            val button = JButton("Action")

            // Define the action to be performed when the button is clicked
            button.addActionListener { value.action.invoke() }

            // Adjust the layout and add components
            panel.add(nameLabel, BorderLayout.CENTER)
            panel.add(button, BorderLayout.EAST)
            panel.border = JBUI.Borders.empty(2, 5)

            return panel
        }

        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
    }
}

fun main() {
    val jbList = JBList<ListItem>()
    val model = DefaultListModel<ListItem>()

    // Add items to the model with associated actions
    model.addElement(ListItem("Item 1", { /* Action for Item 1 */ }))
    model.addElement(ListItem("Item 2", { /* Action for Item 2 */ }))
    model.addElement(ListItem("Item 3", { /* Action for Item 3 */ }))

    jbList.model = model
    jbList.cellRenderer = CustomListCellRenderer()

    // Add the jbList to your UI
}
