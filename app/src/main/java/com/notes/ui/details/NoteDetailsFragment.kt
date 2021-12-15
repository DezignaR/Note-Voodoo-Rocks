package com.notes.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.notes.App
import com.notes.R
import com.notes.data.NoteDbo
import com.notes.databinding.FragmentNoteDetailsBinding
import com.notes.di.DependencyManager
import com.notes.ui.RootActivity
import com.notes.ui._base.FragmentNavigator
import com.notes.ui._base.ViewBindingFragment
import com.notes.ui._base.findImplementationOrThrow
import com.notes.ui.list.NoteListFragment

class NoteDetailsFragment : ViewBindingFragment<FragmentNoteDetailsBinding>(
    FragmentNoteDetailsBinding::inflate
) {
    companion object {
        const val NOTE = "NOTE"

        fun newInstance(note: NoteDbo? = null) = NoteDetailsFragment().apply {
            arguments = Bundle()
                .apply {
                if (note != null)
                    putParcelable(NOTE,note)
            }
        }
    }


    private val viewModel by lazy { DependencyManager.noteDetailViewModel() }

    private fun setupToolbar(toolbar: Toolbar) {
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            toolbar.setNavigationIcon(R.drawable.ic_menu)
            toolbar.setNavigationOnClickListener {
                findImplementationOrThrow<FragmentNavigator>()
                    .navigateTo(
                        NoteListFragment()
                    )
            }
        }
    }


    override fun onViewBindingCreated(
        viewBinding: FragmentNoteDetailsBinding,
        savedInstanceState: Bundle?
    ) {
        super.onViewBindingCreated(viewBinding, savedInstanceState)

        arguments?.let {

            viewModel.setNoteEdit(it.getParcelable(NOTE))
        }
        setupToolbar(viewBinding.toolbar)
        viewBinding.ivApply.setOnClickListener {
            viewModel.createNote(
                title = viewBinding.etTitle.text.toString(),
                content = viewBinding.etDescription.text.toString()
            )
            viewModel.onCreateNoteClick()
        }

        viewModel.navigateToNoteCreation.observe(
            viewLifecycleOwner,
            {
                findImplementationOrThrow<FragmentNavigator>()
                    .navigateTo(
                        NoteListFragment()
                    )
            }
        )
        viewModel.setNoteEdit.observe(
            viewLifecycleOwner,{
                setEditNote(it)
            }
        )
    }
    private fun setEditNote(note: NoteDbo?){
        viewBinding!!.etTitle.setText(note?.title)
        viewBinding!!.etDescription.setText(note?.content
        )
    }
}