/*
BSD 3-Clause License

Copyright (c) 2017, Adrián Alvarado Ramírez
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of the copyright holder nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.mudracr.hiberpostgre.db;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/*
     Machote de HibernateUtil probada en la versión 5.2.2 de Hibernate.
     Este código está basado en códigos de foros (particulamente stackoverflow)
     Se omite el loggeo del error en caso de darse una excepción al crear el
     objeto de tipo SessionFactory.
	En Glassfish 4.1, es necesario sustuir la biblioteca  jboss-logging.jar por la que
     viene en Hibernate, pues la que está por defecto en los módulos no es compatible (no
     contiene la función debugf), por lo que falla al crear el SessionFactory.
     sudo cp <rutalibs>/hibernate-release-5.2.2.Final/lib/required/jboss-logging-3.3.0.Final.jar <ruta glassfish>/modules/
     @author Ing. Adrián Alvarado Ramírez.
*/
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {

            // Por definición se configura el archivo hibernate.cfg.xml
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();
            try {
                sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                // Se destruye el objeto de tipo StandardServiceRegistry. El responsable
                // de destruir este objeto, en el flujo normal es el objeto de tipo
                // SessionFactory, pero como falló su creación, debe destruirse manualmente.
                StandardServiceRegistryBuilder.destroy(registry);
                System.err.println(e.toString());
            }
        }

        return sessionFactory;

    }
}