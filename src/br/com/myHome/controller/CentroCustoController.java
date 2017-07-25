package br.com.myHome.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import br.com.myHome.beans.CentroCusto;
import br.com.myHome.beans.Gestor;

@SuppressWarnings("serial")
@Path("/manutencao")
@Produces(MediaType.APPLICATION_JSON)
public class CentroCustoController extends HttpServlet {
	
	@SuppressWarnings("deprecation")
	public static SessionFactory sF = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	
	@POST
	@Path("/save")
	@Consumes({"multipart/form-data","application/x-www-form-urlencoded"})
	public Response save(@FormParam("codigo") String codigo, @FormParam("descricao") String descricao,
			@FormParam("oid_gestor") Integer oid_gestor,@FormParam("detalhe") String detalhe )throws IOException{
         
		//Abre uma conexao e transação com banco mysql
        Session session = sF.openSession();
        Transaction tx = session.beginTransaction();
         
	    try {
	    	
	    	//Cria objeto a ser inserido no banco
	    	CentroCusto cc = new CentroCusto(); 
	        cc.setOid(null);
	        cc.setCodigo(codigo);
	        cc.setDescricao(descricao);
	        cc.setOid_gestor(oid_gestor);
	        cc.setDetalhe(detalhe);
	        
	        //salva e comita objeto no banco
	        session.save(cc);
	        tx.commit();
	        
	        return Response.ok("Ccusto criado, seu código é : " + cc.getOid()).build();
	        
	    }catch (Exception e) {
    	   e.printStackTrace();
	    }finally{
	    	session.close();
       }
	    return null;
	}
	
	@PUT
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@FormParam("oid") Integer oid,@FormParam("codigo") String codigo,@FormParam("descricao") String descricao,
					 @FormParam("oid_gestor") Integer oid_gestor, @FormParam("detalhe") String detalhe)throws Exception, IOException{
         
	    Session session = sF.openSession();
	    Transaction tx = session.beginTransaction();
         
	    try {
	    	
	    	CentroCusto cc = new CentroCusto(); 
	        cc.setOid(oid);
	        cc.setCodigo(codigo);
	        cc.setDescricao(descricao);
	        cc.setOid_gestor(oid_gestor);
	        cc.setDetalhe(detalhe);
	        
	        session.merge(cc);
	        tx.commit();
	       
	        return Response.ok("Ccusto alterado, seu código é : " + cc.getOid()).build();
	        
	    }catch (Exception e) {
	    	e.printStackTrace();
	    }finally{
    	   session.close();
	    }
	    return null;
	}
	
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@FormParam("codigo") String codigo)throws Exception, IOException{
         
        Session session = sF.openSession();
        Transaction tx = session.beginTransaction();
         
	    try {
	    	
	    	Criteria criteria = session.createCriteria(CentroCusto.class);
	    	criteria.add(Restrictions.eq("codigo", codigo));
			CentroCusto cc = (CentroCusto) criteria.uniqueResult();
			
	       session.delete(cc);
	       tx.commit();
	       
	       return Response.ok("Ccusto " + cc.getOid() + " Removido !").build();
	        
       }catch (Exception e) {
    	   e.printStackTrace();
       }finally{
    	   session.close();
       }
	    return null;
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/findAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll()throws Exception{
		
        Session session = sF.openSession();
        Transaction tx = session.beginTransaction();
        
        ArrayList<Gestor> gestores = new ArrayList<Gestor>();
        
	    try {
	    	
			Criteria criteria = session.createCriteria(CentroCusto.class);
			ArrayList<CentroCusto> cc = (ArrayList<CentroCusto>) criteria.list();
			
			for(CentroCusto centro : cc){
				
				Criteria criteria2 = session.createCriteria(Gestor.class);
				criteria2.add(Restrictions.eq("oid", centro.getOid_gestor()));
				
				gestores = (ArrayList<Gestor>) criteria2.list();
				
				centro.setGestores(gestores);
			}
			
			tx.commit();
			
			return Response.ok(cc.toString()).build();
			
        }catch (Exception e) {
        	e.printStackTrace();
        }finally{
        	session.close();
        }
		return null;
	}
	
	@GET
	@Path("/findByGestor/{oid_gestor}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findByGestor(@PathParam("oid_gestor") Integer oid_gestor)throws Exception{
		
        Session session = sF.openSession();
        Transaction tx = session.beginTransaction();
        
	    try {
	    	
	    	Criteria criteria = session.createCriteria(CentroCusto.class);
	    	criteria.add(Restrictions.eq("oid_gestor", oid_gestor));
			
			CentroCusto cc = (CentroCusto) criteria.uniqueResult();
			tx.commit();
			
		    return Response.ok(cc.toString(), MediaType.APPLICATION_JSON).build();
			
        }catch (Exception e) {
        	e.printStackTrace();
        }finally{
        	session.close();
        }
		return null;
	}
}
