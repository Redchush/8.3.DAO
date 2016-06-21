package root.dao;

import root.dao.exception.DaoException;
import root.model.Answer;

import java.util.List;


public interface AnswerDao extends AbstractDao<Answer>  {
    @Override
    List<Answer> findAll() throws DaoException;

    @Override
    Answer findEntityById(int id) throws DaoException;

    @Override
    boolean delete(int id) throws DaoException;

    @Override
    boolean delete(Answer entity) throws DaoException;

    @Override
    boolean create(Answer entity) throws DaoException;

    @Override
    Answer update(Answer entity) throws DaoException;
}
