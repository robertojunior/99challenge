package br.com.faru.a99challenge.presentation.person;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.List;

import javax.inject.Inject;

import br.com.faru.a99challenge.R;
import br.com.faru.a99challenge.di.Injector;
import br.com.faru.a99challenge.model.Person;
import br.com.faru.a99challenge.presentation.widget.VerticalRecyclerView;
import br.com.faru.a99challenge.util.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonsFragment extends Fragment implements PersonsContract.View {

    @BindView(R.id.progress_view)
    CircularProgressView progressView;

    @BindView(R.id.list_view)
    VerticalRecyclerView characterList;

    @Inject
    PersonsPresenter presenter;

    private PersonsAdapter adapter;

    OnPersonClickListener onPersonClickListener = this::onClick;

    public interface OnPersonClickListener {
        void onClick(Person person);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_persons, container, false);
        ButterKnife.bind(this, view);

        Injector.getInstance().getComponent().inject(this);

        adapter = new PersonsAdapter(onPersonClickListener);
        characterList.setAdapter(adapter);

        presenter.setView(this);
        presenter.onCreate();

        return view;
    }

    public static PersonsFragment newInstance() {
        return new PersonsFragment();
    }

    @Override
    public void showResults(List<Person> persons) {
        adapter.add(persons);
    }

    @Override
    public void setProgressIndicator(boolean active) {
        progressView.setVisibility(active ? View.VISIBLE : View.GONE);
    }

    @Override
    public void goToPerson(Person person) {
        Navigation.goToPerson(getContext(), person);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void onClick(Person person) {
        presenter.onClick(person);
    }

}
