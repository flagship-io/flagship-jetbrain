package com.github.flagshipio.jetbrain.toolWindow

import com.intellij.openapi.Disposable
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.ui.JBColor
import com.intellij.util.ui.JBUI
import com.jgoodies.forms.layout.ColumnSpec
import com.jgoodies.forms.layout.FormLayout
import com.jgoodies.forms.layout.RowSpec
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import java.awt.event.ActionEvent
import javax.swing.*
import javax.swing.border.EmptyBorder
import javax.swing.border.LineBorder


class ManageConfigurationPanel:
         SimpleToolWindowPanel(false, false), Disposable
         {
             private fun customPanel(): JPanel {
                 val contentPane = JPanel();
		contentPane.setLayout(BorderLayout(0, 0));

		var lblNewLabel = JLabel("Add configuration");
                 lblNewLabel.border = JBUI.Borders.empty(10, 10, 0, 0)
		contentPane.add(lblNewLabel, BorderLayout.NORTH);

		var panel = JPanel();
		contentPane.add(panel, BorderLayout.CENTER);

		var btnNewButton = JButton("From credentials");
                 btnNewButton.addActionListener { e: ActionEvent? ->
                     updateContent(customPanel1())
                 }

		panel.setLayout(
            FormLayout(
				"center:460px",
				"center:62px center:62px"
				)
        );
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnNewButton, "1, 1, center, center");

		var btnNewButton_1 = JButton("From file");
                 btnNewButton_1.addActionListener { e: ActionEvent? ->
                     updateContent(customPanel2())
                 }
		btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
                 panel.border = JBUI.Borders.emptyTop(10)
		panel.add(btnNewButton_1, "1, 2, center, center");
                 return contentPane
             }

             private fun customPanel1(): JPanel {
                 val contentPane = JPanel();
                 contentPane.setLayout(BorderLayout(0, 0));

                 val panel_1 = JPanel()
                 contentPane.add(panel_1, BorderLayout.SOUTH)


                 val btnNewButton_3 = JButton("Cancel")
                 btnNewButton_3.addActionListener { e: ActionEvent? ->
                     updateContent(customPanel())
                 }
                 panel_1.add(btnNewButton_3)

                 val btnNewButton_2 = JButton("Save")
                 panel_1.add(btnNewButton_2)

                 val lblNewLabel = JLabel("Add configuration")
                 lblNewLabel.setBorder(EmptyBorder(10, 10, 0, 0))
                 contentPane.add(lblNewLabel, BorderLayout.NORTH)

                 val panel = JPanel()
                 panel.setBorder(EmptyBorder(10, 0, 0, 0))
                 contentPane.add(panel, BorderLayout.CENTER)
                 panel.setLayout(FormLayout("7dlu center:120px center:260px","25px 15dlu 25px 7dlu 25px 7dlu 25px 7dlu 25px"))

                 val lblNewLabel_3 = JLabel("Name")
                 lblNewLabel_3.setBorder(EmptyBorder(0, 0, 0, 8))
                 panel.add(lblNewLabel_3, "2, 1, right, default")

                 val textField_2 = JTextField()
                 textField_2.setBorder(EmptyBorder(0, 0, 0, 8))
                 panel.add(textField_2, "3, 1, fill, default")
                 textField_2.setColumns(10)

                 val lblNewLabel_4 = JLabel("Client ID")
                 lblNewLabel_4.setBorder(EmptyBorder(0, 0, 0, 8))
                 panel.add(lblNewLabel_4, "2, 3, right, default")

                 val textField_3 = JTextField()
                 textField_3.setBorder(EmptyBorder(0, 0, 0, 8))
                 panel.add(textField_3, "3, 3, fill, default")
                 textField_3.setColumns(10)

                 val lblNewLabel_6 = JLabel("Client Secret")
                 lblNewLabel_6.setBorder(EmptyBorder(0, 0, 0, 8))
                 panel.add(lblNewLabel_6, "2, 5, right, default")

                 val textField_5 = JTextField()
                 textField_5.setBorder(EmptyBorder(0, 0, 0, 8))
                 panel.add(textField_5, "3, 5, fill, default")
                 textField_5.setColumns(10)

                 val lblNewLabel_5 = JLabel("Account ID")
                 lblNewLabel_5.setBorder(EmptyBorder(0, 0, 0, 8))
                 panel.add(lblNewLabel_5, "2, 7, right, default")

                 val textField_4 = JTextField()
                 textField_4.setBorder(EmptyBorder(0, 0, 0, 8))
                 panel.add(textField_4, "3, 7, fill, default")
                 textField_4.setColumns(10)

                 val lblNewLabel_1 = JLabel("Account Environment ID")
                 lblNewLabel_1.setBorder(EmptyBorder(0, 0, 0, 8))
                 panel.add(lblNewLabel_1, "2, 9, right, default")

                 val textField = JTextField()
                 textField.setBorder(EmptyBorder(0, 0, 0, 8))
                 panel.add(textField, "3, 9, fill, default")
                 textField.setColumns(10)

                 return contentPane
             }

             private fun customPanel2(): JPanel {


                 val contentPane = JPanel();
                 contentPane.setLayout(BorderLayout(0, 0));

                 val panel_1 = JPanel()
                 contentPane.add(panel_1, BorderLayout.SOUTH)

                 val btnNewButton_3 = JButton("Cancel")
                 btnNewButton_3.addActionListener { e: ActionEvent? ->
                     updateContent(customPanel())
                 }
                 panel_1.add(btnNewButton_3)

                 val btnNewButton_2 = JButton("Save")
                 panel_1.add(btnNewButton_2)

                 var lblNewLabel = JLabel("Add configuration");
                 lblNewLabel.border = JBUI.Borders.empty(10, 10, 0, 0)
                 contentPane.add(lblNewLabel, BorderLayout.NORTH);

                 var panel = JPanel();
                 contentPane.add(panel, BorderLayout.CENTER);



                 var btnNewLabel = JLabel("/path/to/file.yaml");
                 btnNewLabel.border = LineBorder(JBColor.BLACK)
                 btnNewLabel.preferredSize = Dimension(600, 30)

                 panel.setLayout(
                     FormLayout(
                         "center:210px center:100px",
                         "center:120px"
                     )
                 );
                 btnNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                 panel.add(btnNewLabel, "1, 1, center, center");

                 var btnNewButton_1 = JButton("Browser");
                 btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
                 panel.border = JBUI.Borders.empty(10, 40, 0, 0)
                 panel.add(btnNewButton_1, "2, 1, center, center");
                 return contentPane
             }


             private fun createControlsPanel(): JPanel {
                 val controlsPanel = JPanel()
                 controlsPanel.layout = BoxLayout(controlsPanel, BoxLayout.Y_AXIS)
                 val textBox = JLabel("Add configuration")
                 val refreshDateAndTimeButton =  JButton("From credentials")
                 refreshDateAndTimeButton.addActionListener { e: ActionEvent? -> updateContent(customPanel1()) }
                 val hideToolWindowButton = JButton("From file")
                 hideToolWindowButton.addActionListener { e: ActionEvent? ->
                     updateContent(customPanel2())
                 }

                 refreshDateAndTimeButton.alignmentX = Component.CENTER_ALIGNMENT
                 hideToolWindowButton.alignmentX = Component.CENTER_ALIGNMENT

                 controlsPanel.add(textBox, BorderLayout.PAGE_START)
                 controlsPanel.add(refreshDateAndTimeButton)
                 controlsPanel.add(hideToolWindowButton)
                 return controlsPanel
             }

             private fun createControlsPanel1(): JPanel {
                 val controlsPanel = JPanel()
                 val refreshDateAndTimeButton = JButton("Refresh2")
                 refreshDateAndTimeButton.addActionListener { e: ActionEvent? -> updateContent(createControlsPanel())}
                 controlsPanel.add(refreshDateAndTimeButton)
                 val hideToolWindowButton = JButton("Hide")
                 hideToolWindowButton.addActionListener { e: ActionEvent? ->
                     updateContent(createControlsPanel1())
                 }
                 controlsPanel.add(hideToolWindowButton)
                 return controlsPanel
             }

             override fun dispose() {
             }

             init {
                 this.setContent(customPanel())
             }

             private fun updateContent(content: JPanel){
                 this.setContent(content)
             }
         }

